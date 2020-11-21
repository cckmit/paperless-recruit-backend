package com.xiaohuashifu.recruit.authentication.service.granter;

import com.xiaohuashifu.recruit.authentication.service.token.OpenidAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.App;
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
 * 描述：处理grant_type=openid类型的认证请求，并封装成类型OpenidAuthenticationToken，交给OpenidAuthenticationProvider处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 11:16
 */
public class OpenidGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "openid";

    private final AuthenticationManager authenticationManager;

    public OpenidGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String app = parameters.get("app");
        String code = parameters.get("code");
        parameters.remove("code");

        Authentication userAuth = new OpenidAuthenticationToken(App.valueOf(app), code);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate.");
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
