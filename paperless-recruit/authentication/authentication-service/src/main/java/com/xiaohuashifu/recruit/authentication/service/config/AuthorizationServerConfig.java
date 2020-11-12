package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.service.enhancer.JwtTokenEnhancer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * TokenStore这里使用JwtTokenStore
     */
    private final TokenStore tokenStore;

    /**
     * 因为使用JwtTokenStore，因此需要提供AccessTokenConverter
     */
    private final AccessTokenConverter accessTokenConverter;

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 授权类型
     */
    private final TokenGranter tokenGranter;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                                     TokenStore tokenStore, AccessTokenConverter accessTokenConverter,
                                     PasswordEncoder passwordEncoder, TokenGranter tokenGranter) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
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
        // 把TokenEnhancer构造成TokenEnhancerChain
        // 因为accessTokenConverter自带TokenEnhancer，因此需要把它带上
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(new JwtTokenEnhancer());
        enhancerList.add((TokenEnhancer) accessTokenConverter);
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(enhancerList);

        // 配置AuthenticationManager和UserDetailsService
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .tokenGranter(tokenGranter);
    }
}
