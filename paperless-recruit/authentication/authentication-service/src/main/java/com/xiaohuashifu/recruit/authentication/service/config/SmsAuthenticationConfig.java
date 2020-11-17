package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import com.xiaohuashifu.recruit.authentication.service.provider.SmsAuthenticationProvider;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 描述：短信验证码认证的配置器
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 20:28
 */
@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Reference
    private SmsLoginService smsLoginService;
    @Reference
    private UserService userService;

    @Override
    public void configure(HttpSecurity http) {
        // 添加provider
        http.authenticationProvider(new SmsAuthenticationProvider(smsLoginService, userService));
    }

}