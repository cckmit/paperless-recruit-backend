package com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.processor;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
import com.xiaohuashifu.recruit.facade.service.request.OAuthTokenPostRequest;
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
import java.util.HashMap;
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
     * 用户的 refresh-token 的 redis key 模式，{0}是用户编号
     */
    protected static final String REFRESH_TOKEN_REDIS_KEY_PATTERN = "refresh-token:user-id:{0}";

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
     * @param request 请求
     * @return 认证结果
     */
    @Override
    public AccessTokenVO authenticate(HttpHeaders httpHeaders, OAuthTokenPostRequest request) {
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", request.getGrantType() != null ? request.getGrantType().getGrantType() : null);
        body.put("principal", request.getPrincipal());
        body.put("password", request.getPassword());
        body.put("app", request.getApp() != null ? request.getApp().name() : null);
        body.put("code", request.getCode());
        body.put("phone", request.getPhone());
        body.put("authCode", request.getAuthCode());
        body.put("refreshToken", request.getRefreshToken());

        httpHeaders.setBasicAuth(encodedCredentials);
        beforeProcess(httpHeaders, body);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(oauthServiceUrl, httpEntity, String.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ResponseEntityException(responseEntity);
        }
        return afterProcess(responseEntity);
    }

    /**
     * 在进行处理之前的操作
     *
     * @param httpHeaders Http 头
     * @param body 请求体
     */
    protected abstract void beforeProcess(HttpHeaders httpHeaders, Map<String, String> body);

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
        String tokenType = result.getString("token_type");
        String refreshToken = result.getString("refresh_token");

        // 解析刷新令牌
        Jwt refreshTokenJwt = JwtHelper.decode(refreshToken);
        JSONObject refreshTokenJwtJson = JSONObject.parseObject(refreshTokenJwt.getClaims());
        Long refreshTokenExpireTime = refreshTokenJwtJson.getLong("exp");
        Long userId = refreshTokenJwtJson.getLong("user_name");

        // 缓存 refresh-token
        String redisKey = MessageFormat.format(REFRESH_TOKEN_REDIS_KEY_PATTERN, userId);
        redisTemplate.opsForValue().set(redisKey, refreshToken);
        redisTemplate.expireAt(redisKey, new Date(refreshTokenExpireTime * 1000));

        // 解析令牌
        Jwt accessTokenJwt = JwtHelper.decode(accessToken);
        JSONObject accessTokenJwtJson = JSONObject.parseObject(accessTokenJwt.getClaims());
        Long accessTokenExpireTime = accessTokenJwtJson.getLong("exp");

        // 返回 access token
        return AccessTokenVO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

}
