package com.xiaohuashifu.recruit.authentication.service.granter;

import com.xiaohuashifu.recruit.authentication.service.token.OpenidAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
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
 * 描述：处理 grant_type=openid 类型的认证请求，并封装成类型 OpenidAuthenticationToken，
 *          交给 OpenidAuthenticationProvider 处理
 *
 * @author: xhsf
 * @create: 2020/11/21 11:16
 */
public class OpenidGranter extends AbstractTokenGranter {
    /**
     * 认证类型
     */
    private static final String GRANT_TYPE = "openid";

    /**
     * openid 认证参数的键
     */
    private static final String OPENID_APP_KEY = "app";

    /**
     * openid 认证参数的键
     */
    private static final String OPENID_CODE_KEY = "code";

    private final AuthenticationManager authenticationManager;

    public OpenidGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String app = parameters.get(OPENID_APP_KEY);
        String code = parameters.get(OPENID_CODE_KEY);
        parameters.remove(OPENID_CODE_KEY);

        Authentication userAuth = new OpenidAuthenticationToken(AppEnum.valueOf(app), code);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate.");
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
