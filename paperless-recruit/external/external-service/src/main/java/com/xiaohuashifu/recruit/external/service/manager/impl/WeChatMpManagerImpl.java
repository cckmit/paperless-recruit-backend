package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.GenderEnum;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import com.xiaohuashifu.recruit.external.service.manager.impl.constant.WeChatMpDetails;
import com.xiaohuashifu.recruit.external.service.pojo.dto.AccessTokenDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpUserInfoDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpWatermarkDTO;
import org.apache.axis.encoding.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
     * 请求 code2Session 的 url
     */
    @Value("${wechat.mp.code-2-session-url}")
    private String code2SessionUrl;

    /**
     * 获取 access_token 的 url
     */
    @Value("${wechat.mp.access-token-url}")
    private String accessTokenUrl;

    /**
     * 微信小程序 access-token 的 redis key 前缀名
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
     * 通过 code 获取封装过的 Code2SessionDTO
     *
     * @param code String
     * @param app 微信小程序类别
     * @return Code2SessionDTO
     */
    @Override
    public Optional<Code2SessionDTO> getCode2Session(String code, AppEnum app) {
        // 获取 Code2SessionDTO
        String url = MessageFormat.format("{0}?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code",
                code2SessionUrl, weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app), code);
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return Optional.ofNullable(responseEntity.getBody());
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
        Optional<Code2SessionDTO> code2SessionDTO = getCode2Session(code, app);
        if (code2SessionDTO.isEmpty()) {
            return Optional.empty();
        }
        String sessionKey = code2SessionDTO.get().getSession_key();

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
        String url = MessageFormat.format("{0}?grant_type=client_credential&appid={1}&secret={2}",
                accessTokenUrl, weChatMpDetails.getAppId(app), weChatMpDetails.getSecret(app));
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
        System.out.println(result);

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
