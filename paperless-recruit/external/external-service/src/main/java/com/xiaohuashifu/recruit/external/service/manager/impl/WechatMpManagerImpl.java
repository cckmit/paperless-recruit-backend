package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import com.xiaohuashifu.recruit.external.service.manager.impl.constant.WechatMpDetails;
import com.xiaohuashifu.recruit.external.service.pojo.dto.AccessTokenDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @create: 2020/11/20 15:53
 */
@Component
public class WechatMpManagerImpl implements WechatMpManager {

    private static final Logger logger = LoggerFactory.getLogger(WechatMpManagerImpl.class);

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

    private final WechatMpDetails wechatMpDetails;

    private final RestTemplate restTemplate;

    private final StringRedisTemplate redisTemplate;

    public WechatMpManagerImpl(WechatMpDetails wechatMpDetails, RestTemplate restTemplate,
                               StringRedisTemplate redisTemplate) {
        this.wechatMpDetails = wechatMpDetails;
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
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Optional.empty();
        }

        // 获取 Code2SessionDTO
        String url = MessageFormat.format("{0}?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code",
                code2SessionUrl, wechatMpDetails.getAppId(app), wechatMpDetails.getSecret(app), code);
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
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Optional.empty();
        }

        // 获取 access-token
        String redisKey = ACCESS_TOKEN_REDIS_KEY_PREFIX + ":" + app.name();
        return Optional.ofNullable(redisTemplate.opsForValue().get(redisKey));
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
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return false;
        }

        // 获取 access-token
        String url = MessageFormat.format("{0}?grant_type=client_credential&appid={1}&secret={2}",
                accessTokenUrl, wechatMpDetails.getAppId(app), wechatMpDetails.getSecret(app));
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

}
