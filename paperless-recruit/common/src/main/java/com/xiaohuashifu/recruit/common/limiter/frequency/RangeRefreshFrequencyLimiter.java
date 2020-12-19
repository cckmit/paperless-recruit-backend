package com.xiaohuashifu.recruit.common.limiter.frequency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 范围刷新限频器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public class RangeRefreshFrequencyLimiter implements FrequencyLimiter{

    /**
     * 范围刷新的限频 Redis Key 前缀
     */
    private static final String RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX = "frequency-limit:range-fresh:";

    /**
     * 范围刷新的限频 Lua 脚本
     */
    private static final String RANGE_REFRESH_FREQUENCY_LIMIT_LUA =
                    "-- 获取 key 对应的 tokens\n" +
                    "local tokens = redis.call('SMEMBERS', KEYS[1])\n" +
                    "\n" +
                    "-- 删除 tokens 里面所有过期的 token\n" +
                    "local expiredTokensCount = 0\n" +
                    "for i = 1, #tokens do\n" +
                    "    if not redis.call('GET', tokens[i]) then\n" +
                    "        redis.call('SREM', KEYS[1], tokens[i])\n" +
                    "        expiredTokensCount = expiredTokensCount + 1\n" +
                    "    end\n" +
                    "end\n" +
                    "\n" +
                    "-- 如果未过期的 token 数量大于等于 frequency，return false\n" +
                    "local unexpiredTokensCount = #tokens - expiredTokensCount;\n" +
                    "if unexpiredTokensCount >= tonumber(ARGV[1]) then\n" +
                    "    return false\n" +
                    "end\n" +
                    "\n" +
                    "-- 生成唯一的 token，添加 token 到 redis 里和 key 对应的 set 里，并设置过期时间\n" +
                    "local token = '" + RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + "' .. redis.call('INCR', 'frequency-limit:token:increment-id')\n" +
                    "redis.call('SET', token, '', 'PX', ARGV[2])\n" +
                    "redis.call('SADD', KEYS[1], token)\n" +
                    "redis.call('PEXPIRE', KEYS[1], ARGV[2])\n" +
                    "return true";

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 范围刷新的限频脚本
     */
    private final RedisScript<Boolean> rangeRefreshFrequencyLimitRedisScript;

    public RangeRefreshFrequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        // 范围刷新的限频脚本
        DefaultRedisScript<Boolean> rangeRefreshFrequencyLimitRedisScript = new DefaultRedisScript<>();
        rangeRefreshFrequencyLimitRedisScript.setResultType(Boolean.class);
        rangeRefreshFrequencyLimitRedisScript.setScriptText(RANGE_REFRESH_FREQUENCY_LIMIT_LUA);
        this.rangeRefreshFrequencyLimitRedisScript = rangeRefreshFrequencyLimitRedisScript;
    }

    /**
     * 查询一个键是否被允许操作，范围刷新
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
    public boolean isAllowed(String key, long frequency, long time, TimeUnit unit) {
        key = RANGE_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + key;
        return stringRedisTemplate.execute(rangeRefreshFrequencyLimitRedisScript, Collections.singletonList(key),
                String.valueOf(frequency), String.valueOf(TimeoutUtils.toMillis(time, unit)));
    }

}
