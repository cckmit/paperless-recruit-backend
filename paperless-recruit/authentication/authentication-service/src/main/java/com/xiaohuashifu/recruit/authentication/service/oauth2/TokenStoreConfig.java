package com.xiaohuashifu.recruit.authentication.service.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
     * jwt的签名密钥
     */
    @Value("${jwt.signingKey}")
    private String signingKey;

    /**
     * 使用JWT的令牌，用于替换默认UUID的令牌
     *  即access_token
     * @return JwtTokenStore
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 用于把默认token转换成jwt类型
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 签名密钥
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }

}
