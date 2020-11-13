package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 描述：继承于WebSecurityConfigurerAdapter，
 *
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 19:26
 */
@EnableWebSecurity
// TODO: 2020/11/12 这里不懂
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MessageAuthCodeAuthenticationConfig messageAuthCodeAuthenticationConfigurer;

    public WebSecurityConfig(MessageAuthCodeAuthenticationConfig messageAuthCodeAuthenticationConfigurer) {
        this.messageAuthCodeAuthenticationConfigurer = messageAuthCodeAuthenticationConfigurer;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .csrf().disable()
                .cors()
                .and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(messageAuthCodeAuthenticationConfigurer);
    }

}
