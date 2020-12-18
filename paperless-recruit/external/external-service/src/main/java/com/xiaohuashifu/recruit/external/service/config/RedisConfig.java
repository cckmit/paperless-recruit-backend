package com.xiaohuashifu.recruit.external.service.config;

import com.xiaohuashifu.recruit.common.limiter.frequency.FrequencyLimitAspect;
import com.xiaohuashifu.recruit.common.limiter.frequency.FrequencyLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 描述：Redis 相关配置
 *
 * @author: xhsf
 * @create: 2020/11/23 13:45
 */
@Configuration
public class RedisConfig {

    /**
     * 自增 ID Lua 脚本 classpath 路径
     */
    private static final String INCREMENT_ID_REDIS_LUA_SCRIPT_CLASS_PATH = "/redis/lua/IncrementId.lua";

    /**
     * 自增 ID Redis 脚本
     *
     * @return 自增 id 值
     */
    @Bean("incrementIdRedisScript")
    public RedisScript<Long> incrementIdRedisScript() {
        DefaultRedisScript<Long> incrementIdRedisScript = new DefaultRedisScript<>();
        incrementIdRedisScript.setResultType(Long.class);
        incrementIdRedisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource(INCREMENT_ID_REDIS_LUA_SCRIPT_CLASS_PATH)));
        return incrementIdRedisScript;
    }

    /**
     * 限频器
     *
     * @param stringRedisTemplate StringRedisTemplate
     * @return FrequencyLimiter
     */
    @Bean
    public FrequencyLimiter frequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        return new FrequencyLimiter(stringRedisTemplate);
    }

    /**
     * 限频切面
     *
     * @param frequencyLimiter FrequencyLimiter
     * @return FrequencyLimitAspect
     */
    @Bean
    public FrequencyLimitAspect frequencyLimitAspect(FrequencyLimiter frequencyLimiter) {
        return new FrequencyLimitAspect(frequencyLimiter);
    }

}
