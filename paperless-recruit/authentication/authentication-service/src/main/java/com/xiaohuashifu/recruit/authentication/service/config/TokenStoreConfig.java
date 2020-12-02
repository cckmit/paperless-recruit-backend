package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 描述：Token 存储的配置
 *
 * @author: xhsf
 * @create: 2020/11/10 19:46
 */
@Configuration
public class TokenStoreConfig {
    /**
     * JWT 密钥密码
     */
    @Value("${jwt.secret.password}")
    private String password;

    /**
     * JWT 密钥别名
     */
    @Value("${jwt.secret.alias}")
    private String alias;

    /**
     * JWT 密钥存放路径
     */
    @Value("${jwt.secret.path}")
    private String path;

    /**
     * 使用 JWT 的令牌
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 用于把默认 token 转换成 jwt 类型
     *
     * @return JwtAccessTokenConverter
     */
    @Bean(name = "jwtAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 签名密钥
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * JWT 的密钥对（公+私钥）
     *
     * @return KeyPair
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource(path), password.toCharArray());
        return factory.getKeyPair(alias, password.toCharArray());
    }

}
