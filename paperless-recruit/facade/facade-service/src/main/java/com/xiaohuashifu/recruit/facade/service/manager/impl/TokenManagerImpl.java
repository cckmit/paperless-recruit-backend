package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.manager.TokenManager;
import com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.processor.AuthenticationProcessor;
import com.xiaohuashifu.recruit.facade.service.request.TokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.TokenVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * 描述：Token 管理器
 *
 * @author xhsf
 * @create 2021/1/15 20:20
 */
@Component
public class TokenManagerImpl implements TokenManager {

    private final StringRedisTemplate redisTemplate;

    /**
     * 用户的 refresh-token 的 redis key 模式，{0}是用户编号
     */
    protected static final String REFRESH_TOKEN_REDIS_KEY_PATTERN = "refresh-token:user-id:{0}";

    private final List<AuthenticationProcessor> authenticationProcessorList;

    public TokenManagerImpl(StringRedisTemplate redisTemplate,
                            List<AuthenticationProcessor> authenticationProcessorList) {
        this.redisTemplate = redisTemplate;
        this.authenticationProcessorList = authenticationProcessorList;
    }

    /**
     * 认证
     *
     * @param httpHeaders HttpHeaders
     * @param request TokenPostRequest
     * @return TokenVO
     */
    @Override
    public TokenVO authenticate(HttpHeaders httpHeaders, TokenPostRequest request) {
        // 进行认证
        for (AuthenticationProcessor authenticationProcessor : authenticationProcessorList) {
            if (authenticationProcessor.isSupport(request.getGrantType())) {
                return authenticationProcessor.authenticate(httpHeaders, request);
            }
        }

        // 不支持的认证类型
        throw new InvalidGrantException("Unsupported grant type.");
    }

    /**
     * 退出登录
     *
     * @param userId 用户编号
     */
    @Override
    public void logout(Long userId) {
        String redisKey = MessageFormat.format(REFRESH_TOKEN_REDIS_KEY_PATTERN, userId);
        redisTemplate.delete(redisKey);
    }
}
