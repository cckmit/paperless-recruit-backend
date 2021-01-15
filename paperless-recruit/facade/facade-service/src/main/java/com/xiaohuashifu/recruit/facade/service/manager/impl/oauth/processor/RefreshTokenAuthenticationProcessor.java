package com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.processor;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.constant.GrantTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

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
    protected void beforeProcess(HttpHeaders httpHeaders, Map<String, String> body) {
        // 判断 Refresh Token 是否存在且有效
        String refreshToken = body.get("refreshToken");
        Jwt jwt = JwtHelper.decode(refreshToken);
        JSONObject jwtJson = JSONObject.parseObject(jwt.getClaims());
        Long userId = jwtJson.getLong("user_name");
        String redisKey = MessageFormat.format(REFRESH_TOKEN_REDIS_KEY_PATTERN, userId);
        String refreshTokenFromRedis = redisTemplate.opsForValue().get(redisKey);
        if (!Objects.equals(refreshTokenFromRedis, refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token.");
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
