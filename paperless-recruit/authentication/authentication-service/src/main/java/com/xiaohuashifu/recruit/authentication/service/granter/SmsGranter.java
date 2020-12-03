package com.xiaohuashifu.recruit.authentication.service.granter;

import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：处理 grant_type=sms 类型的认证请求，并封装成类型 SmsAuthenticationToken，交给 SmsAuthenticationProvider 处理
 *
 * @author: xhsf
 * @create: 2020/11/12 11:16
 */
public class SmsGranter extends AbstractTokenGranter {
    /**
     * 认证类型
     */
    private static final String GRANT_TYPE = "sms";

    /**
     * sms 认证参数的键
     */
    private static final String SMS_PHONE_KEY = "phone";

    /**
     * sms 认证参数的键
     */
    private static final String SMS_AUTH_CODE_KEY = "authCode";

    private final AuthenticationManager authenticationManager;

    public SmsGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String phone = parameters.get(SMS_PHONE_KEY);
        String authCode = parameters.get(SMS_AUTH_CODE_KEY);
        if (phone == null || authCode == null) {
            throw new InvalidGrantException("Phone and authCode can't be null.");
        }
        parameters.remove(SMS_AUTH_CODE_KEY);

        Authentication userAuth = new SmsAuthenticationToken(phone, authCode);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate phone: " + phone);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
