package com.xiaohuashifu.recruit.authentication.service.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 描述：继承字WebSecurityConfigurerAdapter，
 *      主要是为了获取WebSecurityConfigurerAdapter里的某些Bean类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 19:26
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
