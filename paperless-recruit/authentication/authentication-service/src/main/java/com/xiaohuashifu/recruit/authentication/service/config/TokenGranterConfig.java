package com.xiaohuashifu.recruit.authentication.service.config;

import com.xiaohuashifu.recruit.authentication.service.granter.SmsGranter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述：TokenGranter的配置，自定义
 *      可以添加Granter类型
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 11:20
 */
@Configuration
public class TokenGranterConfig {

    /**
     * 客户端服务
     */
    private final ClientDetailsService clientDetailsService;

    /**
     * 具体实现类为UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;

    /**
     * Spring Security的AuthenticationManager
     */
    private final AuthenticationManager authenticationManager;

    /**
     * TokenStore这里使用JwtTokenStore
     */
    private final TokenStore tokenStore;

    /**
     * TokenEnhancer列表，只要添加成Bean就会自动注入
     */
    private final List<TokenEnhancer> tokenEnhancer;

    /**
     * 验证码模式的验证码生成器
     */
    private RandomValueAuthorizationCodeServices authorizationCodeServices;

    /**
     * 是否重用RefreshToken
     */
    private boolean reuseRefreshToken = true;

    private AuthorizationServerTokenServices tokenServices;

    public TokenGranterConfig(ClientDetailsService clientDetailsService, UserDetailsService userDetailsService,
                              AuthenticationManager authenticationManager, TokenStore tokenStore,
                              List<TokenEnhancer> tokenEnhancer) {
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.tokenEnhancer = tokenEnhancer;
    }

    /**
     * 授权模式
     * @return TokenGranter 这里是一个授权模式代理，里面有所有授权模式
     */
    @Bean
    public TokenGranter tokenGranter() {
        return (String grantType, TokenRequest tokenRequest)->
                new CompositeTokenGranter(getAllTokenGranters()).grant(grantType, tokenRequest);
    }

    /**
     * 所有授权模式：默认的5种模式 + 自定义的模式
     *      可以在这里添加授权模式
     * @return List<TokenGranter>
     */
    private List<TokenGranter> getAllTokenGranters() {
        AuthorizationServerTokenServices tokenServices = tokenServices();
        AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
        OAuth2RequestFactory requestFactory = requestFactory();
        // 获取默认的授权模式
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
        // 添加手机号验证码授权模式
        tokenGranters.add(new SmsGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        return tokenGranters;
    }

    /**
     * 默认的授权模式
     */
    private List<TokenGranter> getDefaultTokenGranters(
            AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices,
            OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 添加授权码模式
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices,
                clientDetailsService, requestFactory));
        // 添加刷新令牌的模式
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加隐士授权模式
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加客户端模式
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加密码模式
        tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                clientDetailsService, requestFactory));
        return tokenGranters;
    }

    private AuthorizationServerTokenServices tokenServices() {
        if (tokenServices != null) {
            return tokenServices;
        }
        tokenServices = createDefaultTokenServices();
        return tokenServices;
    }

    private AuthorizationCodeServices authorizationCodeServices() {
        if (authorizationCodeServices == null) {
            authorizationCodeServices = new InMemoryAuthorizationCodeServices();
        }
        return authorizationCodeServices;
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(reuseRefreshToken);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        addUserDetailsService(tokenServices, userDetailsService);
        return tokenServices;
    }

    /**
     * TokenEnhancer
     *
     * @return TokenEnhancer
     */
    private TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancer);
        return tokenEnhancerChain;
    }

    private void addUserDetailsService(DefaultTokenServices tokenServices, UserDetailsService userDetailsService) {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
        tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
    }
}