package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 描述：继承于 WebSecurityConfigurerAdapter
 *
 * @author: xhsf
 * @create: 2020/11/10 19:26
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SmsAuthenticationConfig smsAuthenticationConfig;

    private final OpenIdAuthenticationConfig openIdAuthenticationConfig;

    private final PasswordAuthenticationConfig passwordAuthenticationConfig;

    public WebSecurityConfig(SmsAuthenticationConfig smsAuthenticationConfig,
                             OpenIdAuthenticationConfig openIdAuthenticationConfig,
                             PasswordAuthenticationConfig passwordAuthenticationConfig) {
        this.smsAuthenticationConfig = smsAuthenticationConfig;
        this.openIdAuthenticationConfig = openIdAuthenticationConfig;
        this.passwordAuthenticationConfig = passwordAuthenticationConfig;
    }

    /**
     * 获取 AuthenticationManager 对象，该对象只能在这获取
     *
     * @return AuthenticationManager
     * @throws Exception .
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        // 不校验，否则资源服务器访问时报 403 错误
        web.ignoring().antMatchers("/oauth/check_token");
    }

    /**
     * Spring Security 安全配置
     *
     * @param http HttpSecurity
     * @throws Exception .
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    // 当前 SecurityFilterChain 匹配那些请求，表示认证
                    // 这里表示匹配任何请求（URL），表示都需要认证
                    .requestMatchers()
                    .anyRequest()
                .and()
                    // 这里是 FilterSecurityInterceptor 拦截器，表示授权
                    // 允许所有请求（URL），表示都不需要授权
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                .and()
                    // 关闭 csrf 保护：跨站请求伪造（Cross-site request forgery）
                    .csrf().disable()
                    // 允许 cors：跨域资源共享（Cross-origin resource sharing）
                    .cors()
                .and()
                    // 关闭 session，任何情况不创建 Cookie
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    // 添加短信验证码认证配置
                    .apply(smsAuthenticationConfig)
                .and()
                    // 添加 openId 认证配置
                    .apply(openIdAuthenticationConfig)
                .and()
                    // 添加 Password 认证配置
                    .apply(passwordAuthenticationConfig);
        // TODO: 2020/11/14 匿名认证过滤器AnonymousAuthenticationFilter，
        //  给匿名用户填充`AnonymousAuthenticationToken`到`SecurityContextHolder`的`Authentication`
        // TODO: 2020/11/14  `AbstractSecurityInterceptor`填充URL对应的角色，和决定授权
        // TODO: 2020/11/14 ExceptionTranslationFilter  捕获处理spring security抛出的异常，异常主要来源于FilterSecurityInterceptor
    }

}
