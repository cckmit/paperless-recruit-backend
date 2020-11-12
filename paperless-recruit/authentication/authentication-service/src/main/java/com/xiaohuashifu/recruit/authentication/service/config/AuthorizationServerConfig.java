package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;

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
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 授权类型
     */
    private final TokenGranter tokenGranter;

    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, TokenGranter tokenGranter) {
        this.passwordEncoder = passwordEncoder;
        this.tokenGranter = tokenGranter;
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
            .and()
                .withClient("test2")
                .secret(passwordEncoder.encode("test2222"))
                .accessTokenValiditySeconds(7200);
    }

    /**
     * 认证服务器配置
     * @param endpoints .
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenGranter(tokenGranter);
    }
}
