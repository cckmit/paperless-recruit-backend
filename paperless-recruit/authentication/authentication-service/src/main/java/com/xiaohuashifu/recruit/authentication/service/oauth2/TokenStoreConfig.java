package com.xiaohuashifu.recruit.authentication.service.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 描述：也就是Token保存在Redis服务器里面
 *      而不是在内存里
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 19:46
 */
@Configuration
public class TokenStoreConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    public TokenStoreConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
