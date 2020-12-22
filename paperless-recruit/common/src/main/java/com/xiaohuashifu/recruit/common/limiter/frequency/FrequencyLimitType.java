package com.xiaohuashifu.recruit.common.limiter.frequency;

/**
 * 描述：限频类型
 *
 * @author xhsf
 * @create 2020/12/18 21:10
 */
public enum FrequencyLimitType {

    /**
     * 固定时间点刷新
     */
    FIXED_POINT_REFRESH,

    /**
     * 固定延迟刷新
     */
    FIXED_DELAY_REFRESH,

    /**
     * 范围刷新
     */
    RANGE_REFRESH

}
