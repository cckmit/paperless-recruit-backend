package com.xiaohuashifu.recruit.external.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 描述：Redis相关配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/23 13:45
 */
@Configuration
public class RedisConfig {
    /**
     * RedisTemplate对象单例
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        // 设置字符串序列化器，这样Spring就会把Redis的key当成字符串处理
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);

        // redis连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean("incrementIdRedisScript")
    public RedisScript<Long> incrementIdRedisScript() {
        DefaultRedisScript<Long> incrementIdRedisScript = new DefaultRedisScript<>();
        incrementIdRedisScript.setResultType(Long.class);
        incrementIdRedisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("/redis/lua/IncrementId.lua")));
        return incrementIdRedisScript;
    }

}
