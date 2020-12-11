package com.xiaohuashifu.recruit.common.aspect.annotation;

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
     * 分布式锁 key，支持 EL 表达式，如#{#user.phone}
     */
    String value();

    /**
     * 过期时间
     */
    long expirationTime() default 0;

    /**
     * 过期时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
