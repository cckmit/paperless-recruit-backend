package com.xiaohuashifu.recruit.common.limiter.frequency;

import com.xiaohuashifu.recruit.common.util.CronUtils;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 描述：限频器工具类
 *
 * @author xhsf
 * @create 2020/12/22 19:09
 */
public class FrequencyLimiterUtils {

    /**
     * 通过 cron 表达式获取过期时间
     *
     * @param cron cron 表达式
     * @param now 当前时间
     * @return 过期时间
     */
    public static long getExpireAt(String cron, Date now) {
        return CronUtils.next(cron, now).getTime();
    }

    /**
     * 通过 time、timeUnit、currentTime 获取过期时间
     *
     * @param refreshTime 刷新时间
     * @param timeUnit    时间单位
     * @param currentTime 当前时间
     * @return 过期时间
     */
    public static long getExpireAt(long refreshTime, TimeUnit timeUnit, long currentTime) {
        return TimeoutUtils.toMillis(refreshTime, timeUnit) + currentTime;
    }

}
