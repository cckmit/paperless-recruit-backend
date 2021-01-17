package com.xiaohuashifu.recruit.facade.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * 描述：Token 存储的配置
 *
 * @author: xhsf
 * @create: 2020/11/10 19:46
 */
@Configuration
public class TokenStoreConfig {

    private final ResourceServerProperties resourceServerProperties;

    public TokenStoreConfig(ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
    }

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
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setVerifierKey(getVerifierKey());
        return jwtAccessTokenConverter;
    }

    /**
     * 获取公钥的方法
     *
     * @return 公钥
     */
    private String getVerifierKey() {
        ObjectMapper objectMapper = new ObjectMapper();
        String pubKey = new RestTemplate().getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class);
        try {
            Map map = objectMapper.readValue(pubKey, Map.class);
            return map.get("value").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}