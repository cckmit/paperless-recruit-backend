package com.xiaohuashifu.recruit.facade.service.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 描述：资源服务器配置
 *
 * @author: xhsf
 * @create: 2020/11/28 14:29
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public ResourceServerConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("swagger/**", "/swagger-ui.html", "swagger-ui.html", "/webjars/**", "" +
                        "/swagger-ui.html/*", "/swagger-resources" , "/swagger-resources/**", "/swagger-ui/**",
                        "/v2/api-docs/**", "/v2/api-docs-ext", "/doc.html")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/tokens")
                .permitAll()
                .mvcMatchers(HttpMethod.GET, "/organizations")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/organizations")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/sms")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/organizations/email-auth-code")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // 关闭 csrf 保护：跨站请求伪造（Cross-site request forgery）
                .csrf().disable()
                // 允许 cors：跨域资源共享（Cross-origin resource sharing）
                .cors()
                .and()
                // 关闭 session，任何情况不创建 Cookie
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                // 处理 accessDenied 异常
                .accessDeniedHandler(customAccessDeniedHandler)
                // 处理认证失败异常
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }

}
