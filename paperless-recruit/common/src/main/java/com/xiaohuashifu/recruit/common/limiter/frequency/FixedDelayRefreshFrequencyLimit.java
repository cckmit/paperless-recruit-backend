package com.xiaohuashifu.recruit.common.limiter.frequency;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 固定延迟刷新限频注解，由 {@link FrequencyLimitAspect} 实现
 *
 * @author xhsf
 * @create 2020-12-18 21:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(FixedDelayRefreshFrequencyLimit.List.class)
public @interface FixedDelayRefreshFrequencyLimit {

    /**
     * 限频的 key，支持 EL 表达式，如#{#user.phone}
     * @see #parameters() 配合该参数，支持占位符填充
     *      如 value = "user:{0}:phone", parameters="#{#user.id}" 会转换成 value = "user:#{#user.id}:phone"
     */
    String key();

    /**
     * 填充到占位符的参数
     */
    String[] parameters() default {};

    /**
     * 频率
     */
    long frequency();

    /**
     * 延迟时间
     */
    long delayTime();

    /**
     * 时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 当获取 token 失败时的错误信息，支持 EL 表达式
     */
    String errorMessage() default "Too many request.";

    /**
     * 描述: 限频注解数组
     *
     * @author xhsf
     * @create 2020-12-18 21:16
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        /**
         * 限频的注解数组
         */
        FixedDelayRefreshFrequencyLimit[] value();

    }
}
