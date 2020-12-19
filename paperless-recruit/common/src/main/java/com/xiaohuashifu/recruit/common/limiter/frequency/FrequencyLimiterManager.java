package com.xiaohuashifu.recruit.common.limiter.frequency;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 限频器管理器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public class FrequencyLimiterManager implements FrequencyLimiter {

    private final FixedPointRefreshFrequencyLimiter fixedPointRefreshFrequencyLimiter;

    private final RangeRefreshFrequencyLimiter rangeRefreshFrequencyLimiter;

    public FrequencyLimiterManager(StringRedisTemplate stringRedisTemplate) {
        this.fixedPointRefreshFrequencyLimiter = new FixedPointRefreshFrequencyLimiter(stringRedisTemplate);
        this.rangeRefreshFrequencyLimiter = new RangeRefreshFrequencyLimiter(stringRedisTemplate);
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
        return rangeRefreshFrequencyLimiter.isAllowed(key, frequency, time, unit);
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
        return fixedPointRefreshFrequencyLimiter.isAllowed(key, frequency, cron);
    }

}
