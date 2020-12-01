package com.xiaohuashifu.recruit.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 描述：Redis相关配置
 *
 * @author: xhsf
 * @create: 2020/11/23 13:45
 */
@Configuration
public class RedisConfig {

    /**
     * 自增id Redis脚本
     * @return 自增id值
     */
    @Bean("incrementIdRedisScript")
    public RedisScript<Long> incrementIdRedisScript() {
        DefaultRedisScript<Long> incrementIdRedisScript = new DefaultRedisScript<>();
        incrementIdRedisScript.setResultType(Long.class);
        incrementIdRedisScript.setScriptSource(new ResourceScriptSource(
                new ClassPathResource("/redis/lua/IncrementId.lua")));
        return incrementIdRedisScript;
    }

}
