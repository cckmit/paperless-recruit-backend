package com.xiaohuashifu.recruit.authentication.service.granter;

import com.xiaohuashifu.recruit.authentication.service.token.OpenIdAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
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
 * 描述：处理 grant_type=openId 类型的认证请求，并封装成类型 OpenIdAuthenticationToken，
 *          交给 OpenIdAuthenticationProvider 处理
 *
 * @author: xhsf
 * @create: 2020/11/21 11:16
 */
public class OpenIdGranter extends AbstractTokenGranter {
    /**
     * 认证类型
     */
    private static final String GRANT_TYPE = "openId";

    /**
     * openId 认证参数的键
     */
    private static final String OPENID_APP_KEY = "app";

    /**
     * openId 认证参数的键
     */
    private static final String OPENID_CODE_KEY = "code";

    private final AuthenticationManager authenticationManager;

    public OpenIdGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        // 获取参数
        Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String app = parameters.get(OPENID_APP_KEY);
        String code = parameters.get(OPENID_CODE_KEY);
        parameters.remove(OPENID_CODE_KEY);

        // 判断 app 和 code 是否为空
        if (StringUtils.isBlank(app) || StringUtils.isBlank(code)) {
            throw new InvalidGrantException("App and code can't be null.");
        }

        // 判断是否包含该 APP
        if (!AppEnum.contains(app)) {
            throw new InvalidGrantException("Invalid app name.");
        }

        // 认证
        Authentication userAuth = new OpenIdAuthenticationToken(AppEnum.valueOf(app), code);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        userAuth = authenticationManager.authenticate(userAuth);
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate.");
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
