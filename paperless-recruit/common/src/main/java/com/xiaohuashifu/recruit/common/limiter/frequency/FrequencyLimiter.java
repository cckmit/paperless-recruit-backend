package com.xiaohuashifu.recruit.common.limiter.frequency;

import java.util.List;

/**
 * 限频器
 *
 * @author xhsf
 * @create 2020/12/18 15:41
 */
public interface FrequencyLimiter {

    /**
     * 查询一个键是否被允许操作
     *
     * @param frequencyLimitType 限频类型
     * @param key 需要限频的键，key的格式最好是 {服务名}:{具体业务名}:{唯一标识符}，如 sms:auth-code:15333333333
     * @param frequency 频率
     * @param time 过期时间戳 or 刷新时间 or 延迟时间
     * @return 是否允许
     */
     boolean isAllowed(FrequencyLimitType frequencyLimitType, String key, long frequency, long time);

    /**
     * 查询多个键是否被允许操作
     *
     * 只要其中一个不被允许，就会失败，并释放已经获取的 tokens
     *
     * @param keys 需要限频的键
     * @param args 参数列表，格式为 [frequency1, time1, frequencyLimitType1,
     *             frequency2, time2, frequencyLimitType2, ..., frequencyN, timeN, frequencyLimitTypeN]
     * @return 是否允许，-1表示允许，其他表示获取失败时的下标
     */
    int isAllowed(List<String> keys, String[] args);

}
