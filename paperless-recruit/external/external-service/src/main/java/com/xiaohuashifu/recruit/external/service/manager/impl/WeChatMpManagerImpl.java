package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.GenderEnum;
import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import com.xiaohuashifu.recruit.external.service.manager.impl.constant.WeChatMpDetails;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpSessionDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpUserInfoDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpWatermarkDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.axis.InternalException;
import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import java.util.concurrent.TimeUnit;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @create: 2020/11/20 15:53
 */
@Component
@Slf4j
public class WeChatMpManagerImpl implements WeChatMpManager {

    /**
     * 微信开放平台请求成功时的错误码
     */
    private static final int WECHAT_OPEN_PLATFORM_REQUEST_SUCCESS_ERROR_CODE = 0;

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

    @Override
    public WeChatMpSessionDTO getSessionByCode(String code, AppEnum app) {
        // 获取 Session
        String url = MessageFormat.format(CODE_TO_SESSION_URL,
                weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app), code);
        String sessionString = restTemplate.getForObject(url, String.class);
        if (StringUtils.isBlank(sessionString)) {
            throw new InternalServiceException("Get session failed, response body is blank.");
        }

        // 封装成 WeChatMpSessionDTO
        JSONObject sessionJsonObject = JSONObject.parseObject(sessionString);
        String openId = sessionJsonObject.getString("openid");
        String sessionKey = sessionJsonObject.getString("session_key");
        String unionId = sessionJsonObject.getString("unionid");
        Integer errorCode = sessionJsonObject.getInteger("errcode");
        String errorMessage = sessionJsonObject.getString("errmsg");
        return new WeChatMpSessionDTO(openId, sessionKey, unionId, errorCode, errorMessage);
    }

    @Override
    public String getAccessToken(AppEnum app) {
        // 获取 access-token
        String redisKey = ACCESS_TOKEN_REDIS_KEY_PREFIX + ":" + app.name();
        String accessToken = redisTemplate.opsForValue().get(redisKey);
        if (accessToken == null) {
            throw new NotFoundServiceException("accessToken", "redisKey", redisKey);
        }
        return accessToken;
    }

    @Override
    public WeChatMpUserInfoDTO getUserInfo(String encryptedData, String iv, String code, AppEnum app) {
        // 通过 code 换取 sessionKey
        WeChatMpSessionDTO code2SessionDTO = getSessionByCode(code, app);
        String sessionKey = code2SessionDTO.getSessionKey();

        // 解析 encryptedData
        try {
            return parseEncryptedData2UserInfo(encryptedData, iv, sessionKey);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
                | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException
                | InvalidKeyException | InvalidParameterSpecException e) {
            log.warn("Parse encryptedData error. encryptedData={}, iv={}, sessionKey={}",
                    encryptedData, iv, sessionKey);
            throw new InternalServiceException("Parse encryptedData error.");
        }
    }

    @Override
    public void sendSubscribeMessage(AppEnum app, String openId,
                                        SendWeChatMpSubscribeMessageRequest request) {
        // 获取 access-token
        String accessToken = getAccessToken(app);

        // 发送消息
        JSONObject subscribeMessage = new JSONObject();
        subscribeMessage.put("touser", openId);
        subscribeMessage.put("template_id", request.getTemplateId());
        subscribeMessage.put("page", request.getPage());
        subscribeMessage.put("data", request.getTemplateData());
        subscribeMessage.put("miniprogram_state", request.getMpType());
        subscribeMessage.put("lang", request.getLanguage());
        String url = MessageFormat.format(SUBSCRIBE_MESSAGE_URL, accessToken);
        String responseString = restTemplate.postForObject(url, subscribeMessage, String.class);
        if (StringUtils.isBlank(responseString)) {
            log.error("Send subscribe message failed, response body is blank. app={}, subscribeMessage={}",
                    app, subscribeMessage);
            throw new InternalException("Send subscribe message failed, response body is blank.");
        }

        // 解析响应信息
        JSONObject responseJsonObject = JSONObject.parseObject(responseString);
        Integer errorCode = responseJsonObject.getInteger("errcode");
        // 发送失败
        if (!Objects.equals(errorCode, WECHAT_OPEN_PLATFORM_REQUEST_SUCCESS_ERROR_CODE)) {
            String errorMessage = responseJsonObject.getString("errmsg");
            log.warn("Send subscribe message failed. " +
                            "errorCode={}, errorMessage={}, app={}, subscribeMessage={}",
                    errorCode, errorMessage, app, subscribeMessage);
            throw new InternalServiceException("Send subscribe message failed.");
        }
    }

    @Override
    public String refreshAccessToken(AppEnum app) {
        // 获取 AccessToken
        String url = MessageFormat.format(ACCESS_TOKEN_URL,
                weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app));
        String accessTokenString = restTemplate.getForObject(url, String.class);
        if (StringUtils.isBlank(accessTokenString)) {
            log.error("Get access token fail, response is blank. app={}", app);
            throw new InternalServiceException("Get access token fail, response is blank.");
        }

        // 解析响应
        JSONObject accessTokenJsonObject = JSONObject.parseObject(accessTokenString);
        Integer errorCode = accessTokenJsonObject.getInteger("errcode");
        // 请求出错
        if (errorCode != null && !Objects.equals(errorCode, WECHAT_OPEN_PLATFORM_REQUEST_SUCCESS_ERROR_CODE)) {
            String errorMessage = accessTokenJsonObject.getString("errmsg");
            log.warn("Refresh access token failed. app={}, errorCode={}, errorMessage={}",
                    app, errorCode, errorMessage);
            throw new InternalServiceException("Refresh access token failed.");
        }
        // 请求成功
        String accessToken = accessTokenJsonObject.getString("access_token");
        long expireTime = accessTokenJsonObject.getLongValue("expires_in");

        // 添加到 Redis
        String redisKey = ACCESS_TOKEN_REDIS_KEY_PREFIX + ":" + app.name();
        redisTemplate.opsForValue().set(redisKey, accessToken);

        // 设置 AccessToken 在 Redis 的过期时间
        redisTemplate.expire(redisKey, expireTime, TimeUnit.SECONDS);
        return accessToken;
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
