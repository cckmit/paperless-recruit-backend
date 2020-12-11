package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.service.provider.OpenIdAuthenticationProvider;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 描述：OpenId 认证的配置器
 *
 * @author: xhsf
 * @create: 2020/11/21 20:28
 */
@Component
public class OpenIdAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Reference
    private AuthOpenIdService authOpenIdService;
    @Reference
    private UserService userService;
    @Reference
    private AuthorityService authorityService;

    @Override
    public void configure(HttpSecurity http) {
        // 添加 provider
        http.authenticationProvider(new OpenIdAuthenticationProvider(authOpenIdService, userService, authorityService));
    }

}
