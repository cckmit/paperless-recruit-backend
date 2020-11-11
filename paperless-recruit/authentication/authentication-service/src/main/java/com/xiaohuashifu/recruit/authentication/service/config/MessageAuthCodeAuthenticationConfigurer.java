package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import com.xiaohuashifu.recruit.authentication.service.filter.MessageAuthCodeAuthenticationFilter;
import com.xiaohuashifu.recruit.authentication.service.provider.MessageAuthCodeAuthenticationProvider;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 描述：短信验证认证的配置器
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 20:28
 */
@Component
public class MessageAuthCodeAuthenticationConfigurer
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final MessageAuthCodeAuthenticationFilter messageAuthCodeAuthenticationFilter;
    private final PhoneLoginService phoneLoginService;
    private final UserService userService;

    public MessageAuthCodeAuthenticationConfigurer(
            AuthenticationFailureHandler authenticationFailureHandler,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            MessageAuthCodeAuthenticationFilter messageAuthCodeAuthenticationFilter,
            PhoneLoginService phoneLoginService, UserService userService) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.messageAuthCodeAuthenticationFilter = messageAuthCodeAuthenticationFilter;
        this.userService = userService;
        this.phoneLoginService = phoneLoginService;
    }

    @Override
    public void configure(HttpSecurity http) {
        messageAuthCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        messageAuthCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        messageAuthCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        http.authenticationProvider(new MessageAuthCodeAuthenticationProvider(phoneLoginService, userService))
                .addFilterAfter(messageAuthCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }



}
