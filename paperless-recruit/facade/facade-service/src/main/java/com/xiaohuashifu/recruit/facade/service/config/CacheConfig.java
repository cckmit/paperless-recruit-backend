package com.xiaohuashifu.recruit.facade.service.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 描述：缓存配置
 *
 * @author xhsf
 * @create 2021/1/9 13:51
 */
@Configuration
public class CacheConfig {

//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
//        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
//        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
//        RedisCacheConfiguration config = RedisCacheConfiguration
//                .defaultCacheConfig();
//        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
//                .fromSerializer(serializer));
//        if (redisProperties.getTimeToLive() != null) {
//            config = config.entryTtl(redisProperties.getTimeToLive());
//        }
//        if (redisProperties.getKeyPrefix() != null) {
//            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
//        }
//        if (!redisProperties.isCacheNullValues()) {
//            config = config.disableCachingNullValues();
//        }
//        if (!redisProperties.isUseKeyPrefix()) {
//            config = config.disableKeyPrefix();
//        }
//        return config;
//
//    }
}
