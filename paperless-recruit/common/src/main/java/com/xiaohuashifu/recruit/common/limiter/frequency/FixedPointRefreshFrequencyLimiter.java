package com.xiaohuashifu.recruit.common.limiter.frequency;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 固定时间点刷新限频器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public class FixedPointRefreshFrequencyLimiter implements FrequencyLimiter{

    /**
     * 固定时间点刷新的限频 Redis Key 前缀
     */
    private static final String FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX =
            "frequency-limit:fixed-point-refresh:";

    /**
     * 固定时间点刷新的限频 lua 脚本
     */
    private static final String FIXED_POINT_REFRESH_FREQUENCY_LIMIT_LUA =
                    "-- 若频率为0直接拒绝请求\n" +
                    "if tonumber(ARGV[1]) == 0 then\n" +
                    "    return false\n" +
                    "end\n" +
                    "\n" +
                    "-- 获取 key 对应的 token 数量\n" +
                    "local tokenNumbers = redis.call('GET', KEYS[1])\n" +
                    "\n" +
                    "-- 如果对应 key 存在，且数量大于等于 frequency，直接返回 false\n" +
                    "if tokenNumbers and tonumber(tokenNumbers) >= tonumber(ARGV[1]) then\n" +
                    "    return false\n" +
                    "end\n" +
                    "\n" +
                    "-- 增加token数量，设置过期时间\n" +
                    "redis.call('INCR', KEYS[1])\n" +
                    "redis.call('PEXPIRE', KEYS[1], ARGV[2])\n" +
                    "return true";

    /**
     * StringRedisTemplate
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * cron 表达式的 CronSequenceGenerator 的缓存
     */
    private final Map<String, CronSequenceGenerator> cronSequenceGeneratorMap = new HashMap<>();

    /**
     * 固定时间点刷新的限频脚本
     */
    private final RedisScript<Boolean> fixedPointRefreshFrequencyLimitRedisScript;

    public FixedPointRefreshFrequencyLimiter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        // 固定时间点刷新的限频脚本
        DefaultRedisScript<Boolean> fixedPointRefreshFrequencyLimitRedisScript = new DefaultRedisScript<>();
        fixedPointRefreshFrequencyLimitRedisScript.setResultType(Boolean.class);
        fixedPointRefreshFrequencyLimitRedisScript.setScriptText(FIXED_POINT_REFRESH_FREQUENCY_LIMIT_LUA);
        this.fixedPointRefreshFrequencyLimitRedisScript = fixedPointRefreshFrequencyLimitRedisScript;
    }

    /**
     * 查询一个键是否被允许操作，固定时间点刷新
     *
     * 如短信验证码服务使用 isAllowed("15333333333", 10, 1, TimeUnit.DAYS); 表示一天只能发送10次短信验证码
     * 如短信验证码服务使用 isAllowed("15333333333", 1, 1, TimeUnit.MINUTES); 表示一分钟只能发送1次短信验证码
     *
     * @param key       需要限频的键，key的格式最好是 {服务名}:{具体业务名}:{唯一标识符}，如 sms:auth-code:15333333333
     * @param frequency 频率
     * @param cron cron表达式
     * @return 是否允许
     */
    public boolean isAllowed(String key, long frequency, String cron) {
        CronSequenceGenerator cronSequenceGenerator = cronSequenceGeneratorMap.getOrDefault(
                cron, cronSequenceGeneratorMap.put(cron, new CronSequenceGenerator(cron)));
        Date now = new Date();
        Date next = cronSequenceGenerator.next(now);
        long timeout = next.getTime() - now.getTime();
        key = FIXED_POINT_REFRESH_FREQUENCY_LIMIT_REDIS_KEY_PREFIX + key;
        return stringRedisTemplate.execute(fixedPointRefreshFrequencyLimitRedisScript, Collections.singletonList(key),
                String.valueOf(frequency), String.valueOf(timeout));
    }

}
