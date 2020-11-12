package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.Arrays;

/**
 * 描述：继承于WebSecurityConfigurerAdapter，
 *
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 19:26
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MessageAuthCodeAuthenticationConfigurer messageAuthCodeAuthenticationConfigurer;

    public WebSecurityConfig(MessageAuthCodeAuthenticationConfigurer messageAuthCodeAuthenticationConfigurer) {
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
                .antMatchers("/login/phone/createMessageAuthCodeAndSend").permitAll() // 无需验证
                .anyRequest()
                .permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                    new Header("Access-control-Allow-Origin","*"),
                    new Header("Access-Control-Expose-Headers","Authorization"))))
                .and()
                .apply(messageAuthCodeAuthenticationConfigurer);
    }
}
