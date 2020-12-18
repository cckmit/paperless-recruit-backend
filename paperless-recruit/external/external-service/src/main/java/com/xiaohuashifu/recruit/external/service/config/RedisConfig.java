package com.xiaohuashifu.recruit.external.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
     * 限频 Lua 脚本 classpath 路径
     */
    private static final String FREQUENCY_LIMIT_LUA_SCRIPT_CLASS_PATH = "/redis/lua/FrequencyLimit.lua";

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
     * 限频 Redis 脚本
     *
     * @return 是否允许
     */
    @Bean("frequencyLimitRedisScript")
    public RedisScript<Long> frequencyLimitRedisScript() {
        DefaultRedisScript<Long> frequencyLimitRedisScript = new DefaultRedisScript<>();
        frequencyLimitRedisScript.setResultType(Long.class);
        frequencyLimitRedisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource(FREQUENCY_LIMIT_LUA_SCRIPT_CLASS_PATH)));
        return frequencyLimitRedisScript;
    }
}
