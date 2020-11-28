package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
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
     * 授权类型
     */
    private final TokenGranter tokenGranter;

    /**
     * 客户端服务，这里使用的是JdbcClientDetailsService
     */
    private final ClientDetailsService clientDetailsService;

    public AuthorizationServerConfig(TokenGranter tokenGranter, ClientDetailsService clientDetailsService) {
        this.tokenGranter = tokenGranter;
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * 客户端配置
     * @param clients .
     * @throws Exception .
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用自定义的客户端服务
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
    }

    /**
     * 认证服务器配置
     * @param endpoints .
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 使用自定义的TokenGranter
        // 这样会使得认证基本上自定义
        // 灵活性较高
        endpoints.tokenGranter(tokenGranter);
    }
}
