package com.xiaohuashifu.recruit.authentication.service.granter;

import com.xiaohuashifu.recruit.authentication.service.token.PasswordAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
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
 * 描述：处理 grant_type=password 类型的认证请求，并封装成类型 PasswordAuthenticationToken，
 *          交给 PasswordAuthenticationProvider 处理
 *
 * @author: xhsf
 * @create: 2020/12/12 11:16
 */
public class PasswordGranter extends AbstractTokenGranter {
    /**
     * 认证类型
     */
    private static final String GRANT_TYPE = "password";

    /**
     * 认证参数的键
     */
    private static final String PRINCIPAL_KEY = "principal";

    /**
     * 认证参数的键
     */
    private static final String PASSWORD_KEY = "password";

    private final AuthenticationManager authenticationManager;

    public PasswordGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String principal = parameters.get(PRINCIPAL_KEY);
        String password = parameters.get(PASSWORD_KEY);
        if (StringUtils.isBlank(principal) || password == null) {
            throw new InvalidGrantException("Principal and password can't be null.");
        }
        parameters.remove(PASSWORD_KEY);

        Authentication userAuth = new PasswordAuthenticationToken(principal, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate.");
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
