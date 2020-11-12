package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 描述：资源服务器配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:04
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 授权配置
                .antMatchers("/login/phone/createMessageAuthCodeAndSend").permitAll() // 无需验证
                .anyRequest() // 所有请求
                .authenticated() // 都需要认证
            .and()
                .csrf().disable();
    }
}
