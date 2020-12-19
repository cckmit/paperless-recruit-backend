package com.xiaohuashifu.recruit.common.limiter.frequency;

import java.lang.annotation.*;

/**
 * 描述: 限频注解数组
 *
 * @author xhsf
 * @create 2020-12-18 21:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrequencyLimits {

    /**
     * 限频的注解数组
     */
    FrequencyLimit[] value();

}
