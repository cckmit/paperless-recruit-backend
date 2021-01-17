package com.xiaohuashifu.recruit.facade.service.authorize;

import java.lang.annotation.*;

/**
 * 描述: 验证是否是资源拥有者，由 {@link com.xiaohuashifu.recruit.facade.service.authorize.OwnerAspect} 实现
 *
 * @author xhsf
 * @create 2021-01-17 21:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Owner {

    /**
     * id，支持 EL 表达式，如 #user.id
     */
    String id();

    /**
     * 要使用的上下文
     */
    Class<? extends Context> context();

}
