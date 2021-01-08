package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.OrderNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 订单号校验，必须 {7位业务号}{yyyyMMddHHmmss}{5位1秒内的自增序号} 全数字
 *
 * @author xhsf
 * @create 2021-01-08
 */
@Documented
@Constraint(validatedBy = {OrderNumberValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OrderNumber.List.class)
public @interface OrderNumber {

    String message() default "The order number must meet the regex of " +
            "^\\d{11}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])([0-5]\\d){2}\\d{5}$.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        OrderNumber[] value();
    }

}
