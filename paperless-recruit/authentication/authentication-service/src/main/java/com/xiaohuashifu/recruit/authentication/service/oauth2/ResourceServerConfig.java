package com.xiaohuashifu.recruit.authentication.service.oauth2;

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

    private final MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;


    public ResourceServerConfig(MyAuthenticationFailureHandler myAuthenticationFailureHandler,
                                MyAuthenticationSuccessHandler myAuthenticationSuccessHandler) {
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单登录，POST方法
                .loginProcessingUrl("/login") // 处理表单登录 URL
                .successHandler(myAuthenticationSuccessHandler) // 处理登录成功
                .failureHandler(myAuthenticationFailureHandler) // 处理登录失败
            .and()
                .authorizeRequests() // 授权配置
                .anyRequest() // 所有请求
                .authenticated() // 都需要认证
            .and()
                .csrf().disable();
    }
}
