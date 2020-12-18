package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.FrequencyLimitService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.UUID;
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

    public FrequencyLimitServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
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

        // 获取 set 里的元素
        // smembers(key)
        Set<String> keys = redisTemplate.opsForSet().members(key);

        // 删除 set 里面过期的元素
        // srem(key+uid)
        int expiredKeysCount = 0;
        // 删除 keys 里面所有过期的 key
        for (String key0 : keys) {
            if (redisTemplate.opsForValue().get(key0) == null) {
                redisTemplate.opsForSet().remove(key, key0);
                expiredKeysCount++;
            }
        }

        // 如果未过期的 key 数量大于 frequency
        // return false
        int unexpiredKeysCount = keys.size() - expiredKeysCount;
        if (unexpiredKeysCount >= frequency) {
            return false;
        }

        // 添加 key 到 redis 里，设置过期时间
        // set(key+uid, "", time, unit)
        // sadd(key, key+uid)
        // expire(key, time, unit)
        // return true
        String member = key + UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(member, "", time, unit);
        redisTemplate.opsForSet().add(key, member);
        redisTemplate.expire(key, time, unit);
        return true;
    }

}
