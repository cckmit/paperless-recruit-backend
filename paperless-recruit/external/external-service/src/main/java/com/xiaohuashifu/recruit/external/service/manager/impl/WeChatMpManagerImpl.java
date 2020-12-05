package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.GenderEnum;
import com.xiaohuashifu.recruit.external.api.dto.SubscribeMessageDTO;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import com.xiaohuashifu.recruit.external.service.manager.impl.constant.WeChatMpDetails;
import com.xiaohuashifu.recruit.external.service.pojo.dto.AccessTokenDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpSessionDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpUserInfoDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpWatermarkDTO;
import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @create: 2020/11/20 15:53
 */
@Component
public class WeChatMpManagerImpl implements WeChatMpManager {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMpManagerImpl.class);

    /**
     * 发送微信订阅消息失败时的错误码
     */
    private static final int SEND_SUBSCRIBE_MESSAGE_FAILED_ERROR_CODE = 0;

    /**
     * 发送模板消息的 url
     */
    private static final String SUBSCRIBE_MESSAGE_URL =
            "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token={0}";

    /**
     * 请求 Session 的 url
     */
    private static final String CODE_TO_SESSION_URL =
            "https://api.weixin.qq.com/sns/jscode2session" +
                    "?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";

    /**
     * 获取 AccessToken 的 url
     */
    private static final String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    /**
     * 微信小程序 AccessToken 的 Redis Key 前缀名
     * 推荐格式为REDIS_KEY:{app}
     */
    public static final String ACCESS_TOKEN_REDIS_KEY_PREFIX = "wechat-mp:access-token";

    private final WeChatMpDetails weChatMpDetails;

    private final RestTemplate restTemplate;

    private final StringRedisTemplate redisTemplate;

    public WeChatMpManagerImpl(WeChatMpDetails weChatMpDetails, RestTemplate restTemplate,
                               StringRedisTemplate redisTemplate) {
        this.weChatMpDetails = weChatMpDetails;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过 code 获取封装过的 WeChatMpSessionDTO
     *
     * @param code String
     * @param app 微信小程序类别
     * @return WeChatMpSessionDTO
     */
    @Override
    public Optional<WeChatMpSessionDTO> getSessionByCode(String code, AppEnum app) {
        // 获取 Session
        String url = MessageFormat.format(CODE_TO_SESSION_URL,
                weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app), code);
        String sessionString = restTemplate.getForObject(url, String.class);
        if (StringUtils.isBlank(sessionString)) {
            return Optional.empty();
        }

        // 封装成 WeChatMpSessionDTO
        JSONObject sessionJsonObject = JSONObject.parseObject(sessionString);
        String openId = sessionJsonObject.getString("openid");
        String sessionKey = sessionJsonObject.getString("session_key");
        String unionId = sessionJsonObject.getString("unionid");
        Integer errorCode = sessionJsonObject.getInteger("errcode");
        String errorMessage = sessionJsonObject.getString("errmsg");
        WeChatMpSessionDTO code2SessionDTO = new WeChatMpSessionDTO(openId, sessionKey, unionId, errorCode, errorMessage);
        return Optional.of(code2SessionDTO);
    }

    /**
     * 获取 access-token
     *
     * @param app 具体的微信小程序类型
     * @return access-token
     */
    @Override
    public Optional<String> getAccessToken(AppEnum app) {
        // 获取 access-token
        String redisKey = ACCESS_TOKEN_REDIS_KEY_PREFIX + ":" + app.name();
        return Optional.ofNullable(redisTemplate.opsForValue().get(redisKey));
    }

    /**
     * 解密 encryptedData 获取用户信息
     *
     * @param encryptedData wx.getUserInfo() 返回值
     * @param iv wx.getUserInfo() 返回值
     * @param code wx.login() 的返回值
     * @param app 具体的微信小程序类型
     * @return 若获取失败则返回 null
     */
    @Override
    public Optional<WeChatMpUserInfoDTO> getUserInfo(String encryptedData, String iv, String code, AppEnum app) {
        // 通过 code 换取 sessionKey
        Optional<WeChatMpSessionDTO> code2SessionDTO = getSessionByCode(code, app);
        if (code2SessionDTO.isEmpty()) {
            return Optional.empty();
        }
        String sessionKey = code2SessionDTO.get().getSessionKey();

        // 解析 encryptedData
        try {
            return Optional.of(parseEncryptedData2UserInfo(encryptedData, iv, sessionKey));
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
                | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException
                | InvalidKeyException | InvalidParameterSpecException e) {
            logger.warn("Parse encryptedData error. encryptedData={}, iv={}, sessionKey={}",
                    encryptedData, iv, sessionKey);
            return Optional.empty();
        }
    }

    /**
     * 发送模板消息
     *
     * @param app 具体的微信小程序类型
     * @param subscribeMessageDTO 模板消息
     * @return 发送结果
     */
    @Override
    public boolean sendSubscribeMessage(AppEnum app, SubscribeMessageDTO subscribeMessageDTO) {
        // 获取 access-token
        Optional<String> accessToken = getAccessToken(app);
        if (accessToken.isEmpty()) {
            logger.error("Can't get access token. app={}", app);
            return false;
        }

        // 发送消息
        String url = MessageFormat.format(SUBSCRIBE_MESSAGE_URL, accessToken.get());
        String responseString = restTemplate.postForObject(url, subscribeMessageDTO, String.class);
        if (StringUtils.isBlank(responseString)) {
            logger.error("Send subscribe message failed, response body is blank. app={}, subscribeMessageDTO={}",
                    app, subscribeMessageDTO);
            return false;
        }

        // 解析响应信息
        JSONObject responseJsonObject = JSONObject.parseObject(responseString);
        Integer errorCode = responseJsonObject.getInteger("errcode");
        if (!Objects.equals(errorCode, SEND_SUBSCRIBE_MESSAGE_FAILED_ERROR_CODE)) {
            logger.warn("Send subscribe message failed. " +
                            "errorCode={}, errorMessage={}, app={}, subscribeMessageDTO={}",
                    errorCode, responseJsonObject.getString("errmsg"), app, subscribeMessageDTO);
            return false;
        }

        // 发送成功
        return true;
    }

    /**
     * 获取新的 access-token
     * 并添加到 redis
     * 并设置过期时间
     *
     * @param app 具体的微信小程序类型
     * @return 刷新是否成功
     */
    @Override
    public boolean refreshAccessToken(AppEnum app) {
        // 获取 access-token
        String url = MessageFormat.format(ACCESS_TOKEN_URL,
                weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app));
        ResponseEntity<AccessTokenDTO> entity = restTemplate.getForEntity(url, AccessTokenDTO.class);
        if (entity.getBody() == null || entity.getBody().getAccess_token() == null) {
            logger.warn("Get access token fail.");
            return false;
        }

        // 添加到 redis
        String redisKey = ACCESS_TOKEN_REDIS_KEY_PREFIX + ":" + app.name();
        redisTemplate.opsForValue().set(redisKey, entity.getBody().getAccess_token());

        // 设置 access-token 在 redis 的过期时间
        redisTemplate.expire(redisKey, entity.getBody().getExpires_in(), TimeUnit.SECONDS);
        return true;
    }

    /**
     * 解析 encryptedData 成 WeChatMpUserInfoDTO
     *
     * @param encryptedData wx.getUserInfo() 返回值
     * @param iv wx.getUserInfo() 返回值
     * @param sessionKey code 换取的 sessionKey
     * @return WeChatMpUserInfoDTO 若解密过程中出错，抛出异常
     */
    private WeChatMpUserInfoDTO parseEncryptedData2UserInfo(String encryptedData, String iv, String sessionKey)
            throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException,
            InvalidParameterSpecException {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        // 如果密钥不足16位，那么就补足。这个 if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }

        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        String result = new String(resultByte, StandardCharsets.UTF_8);

        // 封装成 WeChatMpUserInfoDTO
        JSONObject userInfoJsonObject = JSONObject.parseObject(result);
        JSONObject watermarkJsonObject = userInfoJsonObject.getJSONObject("watermark");
        String appId = watermarkJsonObject.getString("appid");
        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(watermarkJsonObject.getLong("timestamp")), ZoneId.systemDefault());
        WeChatMpWatermarkDTO weChatMpWatermarkDTO = new WeChatMpWatermarkDTO(appId, timestamp);
        String openId = userInfoJsonObject.getString("openId");
        String nickName = userInfoJsonObject.getString("nickName");
        GenderEnum gender = GenderEnum.getGenderById(userInfoJsonObject.getInteger("gender"));
        String city = userInfoJsonObject.getString("city");
        String province = userInfoJsonObject.getString("province");
        String country = userInfoJsonObject.getString("country");
        String avatarUrl = userInfoJsonObject.getString("avatarUrl");
//                String unionId = userInfoJsonObject.getString("unionId");
        return new WeChatMpUserInfoDTO(openId, nickName, gender, city,
                province, country, avatarUrl, null, weChatMpWatermarkDTO);
    }

}
