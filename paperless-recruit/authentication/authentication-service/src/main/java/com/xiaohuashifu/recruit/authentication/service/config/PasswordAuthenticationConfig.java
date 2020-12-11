package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.service.provider.PasswordAuthenticationProvider;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 描述：Password 认证的配置器
 *
 * @author: xhsf
 * @create: 2020/12/12 20:28
 */
@Component
public class PasswordAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final PasswordEncoder passwordEncoder;

    @Reference
    private UserService userService;

    @Reference
    private AuthorityService authorityService;

    public PasswordAuthenticationConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) {
        // 添加 provider
        http.authenticationProvider(new PasswordAuthenticationProvider(userService, authorityService, passwordEncoder));
    }

}
