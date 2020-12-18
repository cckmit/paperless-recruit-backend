package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.FrequencyLimitService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 描述：限频服务实现
 *
 * @private 内部服务
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
@Service
public class FrequencyLimitServiceImpl implements FrequencyLimitService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 限频服务 Redis Key 前缀
     */
    private static final String FREQUENCY_LIMIT_REDIS_KEY_PREFIX = "frequency-limit:";

    /**
     * 限频的脚本
     */
    private final RedisScript<Boolean> frequencyLimitRedisScript;

    public FrequencyLimitServiceImpl(
            StringRedisTemplate redisTemplate,
            @Qualifier("frequencyLimitRedisScript") RedisScript<Boolean> frequencyLimitRedisScript) {
        this.redisTemplate = redisTemplate;
        this.frequencyLimitRedisScript = frequencyLimitRedisScript;
    }

    /**
     * 查询一个键是否被允许操作
     *
     * 如短信验证码服务使用 isAllowed("15333333333", 10, 1, TimeUnit.DAYS); 表示一天只能发送10次短信验证码
     * 如短信验证码服务使用 isAllowed("15333333333", 1, 1, TimeUnit.MINUTES); 表示一分钟只能发送1次短信验证码
     *
     * @param key       需要限频的键，key的格式最好是 {服务名}:{具体业务名}:{唯一标识符}，如 sms:auth-code:15333333333
     * @param frequency 频率
     * @param time      限频时间
     * @param unit      时间单位
     * @return 是否允许
     */
    @Override
    public Boolean isAllowed(String key, Long frequency, Long time, TimeUnit unit) {
        key = FREQUENCY_LIMIT_REDIS_KEY_PREFIX + key;
        return redisTemplate.execute(frequencyLimitRedisScript, Collections.singletonList(key), frequency.toString(),
                String.valueOf(TimeoutUtils.toMillis(time, unit)));
    }

}
