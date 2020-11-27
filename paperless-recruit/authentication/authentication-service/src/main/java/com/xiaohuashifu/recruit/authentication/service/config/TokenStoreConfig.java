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
 * 描述：Token存储的配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 19:46
 */
@Configuration
public class TokenStoreConfig {
    /**
     * JWT密钥密码
     */
    @Value("${jwt.secret.password}")
    private String password;

    /**
     * JWT密钥别名
     */
    @Value("${jwt.secret.alias}")
    private String alias;

    /**
     * JWT密钥存放路径
     */
    @Value("${jwt.secret.path}")
    private String path;

    /**
     * 使用JWT的令牌，用于替换默认UUID的令牌，即access_token
     *
     * 这个实现方式不用管如何进行存储（内存或磁盘），因为它可以把相关信息数据编码存放在令牌里。
     * JwtTokenStore 不会保存任何数据，但是它在转换令牌值以及授权信息方面与 DefaultTokenServices 所扮演的角色是一样的。
     * *
     * 既然jwt是将信息存放在令牌中，那么就得考虑其安全性，因此，OAuth2提供了JwtAccessTokenConverter实现，
     * 添加jwtSigningKey，以此生成秘钥，以此进行签名，只有jwtSigningKey才能获取信息。
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 用于把默认token转换成jwt类型
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
     * JWT的密钥对（公+私钥）
     * @return KeyPair
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource(path), password.toCharArray());
        return factory.getKeyPair(alias, password.toCharArray());
    }

}
