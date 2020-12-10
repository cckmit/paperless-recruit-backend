package com.xiaohuashifu.recruit.external.api.aspect.annotation;

import java.lang.annotation.*;

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
     * 分布式锁 key 前缀
     */
    String keyPrefix();

    /**
     * 分布式锁 key 的参数名
     */
    String keyParameterName();

    /**
     * 过期时间
     */
    long expirationTime() default 0;

}
