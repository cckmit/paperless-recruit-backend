package com.xiaohuashifu.recruit.facade.service.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 描述：资源服务器配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/28 14:29
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                // 关闭csrf保护：跨站请求伪造（Cross-site request forgery）
                .csrf().disable()
                // 允许cors：跨域资源共享（Cross-origin resource sharing）
                .cors()
                .and()
                // 关闭session，任何情况不创建Cookie
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
