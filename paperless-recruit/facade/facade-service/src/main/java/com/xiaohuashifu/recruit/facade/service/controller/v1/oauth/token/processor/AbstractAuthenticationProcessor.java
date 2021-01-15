package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.facade.service.vo.AccessTokenVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * 描述：简单认证的处理器，支持 grantType in [ password, sms, openId ]
 *
 * @author xhsf
 * @create 2021/1/14 19:07
 */
public abstract class AbstractAuthenticationProcessor implements AuthenticationProcessor {

    protected final RestTemplate restTemplate;

    protected final StringRedisTemplate redisTemplate;

    private final String oauthServiceUrl;

    private final String encodedCredentials;

    /**
     * access token 和 refresh token 的 redis key 模式
     */
    protected static final String ACCESS_TOKEN_REFRESH_TOKEN_REDIS_KEY_PATTERN = "access-token:{0}:refresh-token";

    public AbstractAuthenticationProcessor(RestTemplate restTemplate, StringRedisTemplate redisTemplate,
                                           String oauthServiceUrl, String encodedCredentials) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        this.oauthServiceUrl = oauthServiceUrl;
        this.encodedCredentials = encodedCredentials;
    }

    /**
     * 进行认证
     *
     * @param httpHeaders Http 头
     * @param body 请求体
     * @return 认证结果
     */
    @Override
    public Object authenticate(HttpHeaders httpHeaders, Map<String, Object> body) {
        httpHeaders.setBasicAuth(encodedCredentials);
        beforeProcess(httpHeaders, body);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(oauthServiceUrl, httpEntity, String.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity;
        }
        return afterProcess(responseEntity);
    }

    /**
     * 在进行处理之前的操作
     *
     * @param httpHeaders Http 头
     * @param body 请求体
     */
    protected abstract void beforeProcess(HttpHeaders httpHeaders, Map<String, Object> body);

    /**
     * 处理认证成功的情况
     *
     * @param responseEntity 认证请求的响应
     * @return AccessTokenVO
     */
    protected AccessTokenVO afterProcess(ResponseEntity<String> responseEntity) {
        String body = responseEntity.getBody();
        JSONObject result = JSONObject.parseObject(body);
        String accessToken = result.getString("access_token");
        String tokenType =result.getString("token_type");
        String refreshToken = result.getString("refresh_token");
        Jwt jwt = JwtHelper.decode(refreshToken);
        JSONObject jwtJson = JSONObject.parseObject(jwt.getClaims());
        Long expireTime = jwtJson.getLong("exp");

        // 缓存 refresh token
        String accessTokenRefreshTokenRedisKey =
                MessageFormat.format(ACCESS_TOKEN_REFRESH_TOKEN_REDIS_KEY_PATTERN, accessToken);
        redisTemplate.opsForValue().set(accessTokenRefreshTokenRedisKey, refreshToken);
        redisTemplate.expireAt(accessTokenRefreshTokenRedisKey, new Date(expireTime * 1000));

        // 返回 access token
        return AccessTokenVO.builder()
                .accessToken(accessToken)
                .tokenType(tokenType)
                .expireTime(expireTime)
                .build();
    }

}