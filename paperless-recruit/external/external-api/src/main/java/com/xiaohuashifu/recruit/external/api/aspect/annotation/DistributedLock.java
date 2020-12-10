package com.xiaohuashifu.recruit.external.api.aspect.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 分布式锁注解
 *
 * @author xhsf
 * @create 2020-12-10 21:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 分布式锁完整 key
     */
    String value() default "";

    /**
     * 分布式锁完整 key
     * @see #value() 相同
     */
    String key() default "";

    /**
     * 分布式锁 key 前缀
     */
    String keyPrefix() default "";

    /**
     * 分布式锁 key 的参数名
     */
    String keyParameterName() default "";

    /**
     * 过期时间
     */
    long expirationTime() default 0;

    /**
     * 过期时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
