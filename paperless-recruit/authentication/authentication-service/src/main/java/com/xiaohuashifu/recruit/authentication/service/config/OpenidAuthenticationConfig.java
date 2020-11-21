package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.authentication.service.provider.OpenidAuthenticationProvider;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 描述：Openid认证的配置器
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 20:28
 */
@Component
public class OpenidAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Reference
    private AuthOpenidService authOpenidService;
    @Reference
    private UserService userService;
    @Reference
    private PermissionService permissionService;

    @Override
    public void configure(HttpSecurity http) {
        // 添加provider
        http.authenticationProvider(new OpenidAuthenticationProvider(authOpenidService, userService, permissionService));
    }

}
