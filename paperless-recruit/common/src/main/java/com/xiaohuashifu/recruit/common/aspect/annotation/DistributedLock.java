package com.xiaohuashifu.recruit.common.aspect.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 分布式锁注解，由 {@link com.xiaohuashifu.recruit.common.aspect.DistributedLockAspect} 实现
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
     * @see #parameters() 配合该参数，支持占位符填充
     *      如 value = "user:{0}:phone", parameters="#{#user.id}" 会转换成 value = "user:#{#user.id}:phone"
     */
    String value();

    /**
     * 填充到占位符的参数
     */
    String[] parameters() default {};

    /**
     * 过期时间
     */
    long expirationTime() default 0;

    /**
     * 过期时间单位，默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 当获取锁失败时的错误信息，支持 EL 表达式
     */
    String errorMessage() default "Failed to acquire lock.";

}
