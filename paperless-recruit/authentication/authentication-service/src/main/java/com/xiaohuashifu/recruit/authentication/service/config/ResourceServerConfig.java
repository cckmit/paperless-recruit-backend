package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 描述：资源服务器配置
 *
 * @author: xhsf
 * @create: 2020/11/10 16:04
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 授权配置
                .antMatchers("/oauth/**").permitAll() // 无需验证
                .anyRequest() // 所有请求
                .authenticated() // 都需要认证
            .and()
                // 关闭 csrf 保护：跨站请求伪造（Cross-site request forgery）
                .csrf().disable()
                // 允许 cors：跨域资源共享（Cross-origin resource sharing）
                .cors()
            .and()
                // 关闭 session，任何情况不创建 Cookie
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
