package com.xiaohuashifu.recruit.notification.service.config;

import com.xiaohuashifu.recruit.common.aspect.DistributedLockAspect;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：一些单例的配置
 *
 * @author: xhsf
 * @create: 2020/10/30 21:31
 */
@Configuration
public class SingletonConfig {

    /**
     * 分布式锁切面
     *
     * @return DistributedLockAspect
     */
    @Bean
    public DistributedLockAspect distributedLockAspect(RedissonClient redissonClient) {
        return new DistributedLockAspect(redissonClient);
    }

}
