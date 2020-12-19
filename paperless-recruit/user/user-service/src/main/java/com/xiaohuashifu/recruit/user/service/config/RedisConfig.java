package com.xiaohuashifu.recruit.user.service.config;

import com.xiaohuashifu.recruit.common.limiter.frequency.FrequencyLimitAspect;
import com.xiaohuashifu.recruit.common.limiter.frequency.FrequencyLimiter;
import com.xiaohuashifu.recruit.common.limiter.frequency.FrequencyLimiterManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 描述：Redis 相关配置
 *
 * @author: xhsf
 * @create: 2020/11/23 13:45
 */
@Configuration
public class RedisConfig {

    /**
     * 限频器
     *
     * @param stringRedisTemplate StringRedisTemplate
     * @return FrequencyLimiter
     */
    @Bean
    public FrequencyLimiter frequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        return new FrequencyLimiterManager(stringRedisTemplate);
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
