package com.xiaohuashifu.recruit.authentication.service.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 描述：认证服务器配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 15:46
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * Spring Security的AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 具体实现类为UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;

    private final TokenStore tokenStore;

    private final PasswordEncoder passwordEncoder;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokenStore tokenStore, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 客户端配置
     * @param clients .
     * @throws Exception .
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("test")
                .secret(passwordEncoder.encode("test1234"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)
                .scopes("all", "a", "b", "c")
                .authorizedGrantTypes("password")
            .and()
                .withClient("test2")
                .secret(passwordEncoder.encode("test2222"))
                .accessTokenValiditySeconds(7200);
    }

    /**
     * 认证服务器配置
     * @param endpoints .
     * @throws Exception .
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 配置AuthenticationManager和UserDetailsService
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore);
    }
}
