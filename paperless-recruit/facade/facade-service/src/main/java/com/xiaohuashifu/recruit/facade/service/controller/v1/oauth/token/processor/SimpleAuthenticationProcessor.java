package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor;

import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant.GrantTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;
import java.util.Map;

/**
 * 描述：简单认证的处理器，支持 grantType in [ password, sms, openId ]
 *
 * @author xhsf
 * @create 2021/1/14 19:07
 */
@Component
public class SimpleAuthenticationProcessor extends AbstractAuthenticationProcessor {

    /**
     * 支持的认证类型
     */
    private final EnumSet<GrantTypeEnum> SUPPORT_GRANT_TYPE_SET =
            EnumSet.of(GrantTypeEnum.PASSWORD, GrantTypeEnum.SMS, GrantTypeEnum.OPEN_ID);

    public SimpleAuthenticationProcessor(RestTemplate restTemplate, StringRedisTemplate redisTemplate,
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
    protected void beforeProcess(HttpHeaders httpHeaders, Map<String, String> body) {}

    /**
     * 判断是否支持该 grant type 的认证
     *
     * @param grantType 认证类型
     * @return 是否支持
     */
    @Override
    public boolean isSupport(GrantTypeEnum grantType) {
        return SUPPORT_GRANT_TYPE_SET.contains(grantType);
    }

}
