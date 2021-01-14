package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor;

import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant.GrantTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;

/**
 * 描述：刷新 token 的处理器
 *
 * @author xhsf
 * @create 2021/1/14 19:07
 */
@Component
public class RefreshTokenAuthenticationProcessor extends AbstractAuthenticationProcessor {

    public RefreshTokenAuthenticationProcessor(RestTemplate restTemplate, StringRedisTemplate redisTemplate,
                                               @Value("${oauth2.service.url}") String oauthServiceUrl,
                                               @Value("${oauth2.encoded-credentials}") String encodedCredentials) {
        super(restTemplate, redisTemplate, oauthServiceUrl, encodedCredentials);
    }

    /**
     * 在进行处理之前的操作
     *
     * @param httpHeaders Http 头
     * @param body        请求体
     */
    @Override
    protected void beforeProcess(HttpHeaders httpHeaders, Map<String, Object> body) {
        // 获取 Refresh Token
        String accessToken = String.valueOf(body.get("accessToken"));
        String accessTokenRefreshTokenRedisKey =
                MessageFormat.format(ACCESS_TOKEN_REFRESH_TOKEN_REDIS_KEY_PATTERN, accessToken);
        String refreshToken = redisTemplate.opsForValue().get(accessTokenRefreshTokenRedisKey);
        if (refreshToken == null) {
            throw new RuntimeException();
        }
        body.put("refresh_token", refreshToken);
    }

    /**
     * 判断是否支持该 grant type 的认证
     *
     * @param grantType 认证类型
     * @return 是否支持
     */
    @Override
    public boolean isSupport(GrantTypeEnum grantType) {
        return grantType == GrantTypeEnum.REFRESH_TOKEN;
    }

}
