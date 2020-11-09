package com.xiaohuashifu.recruit.common.validator.annotation;


import com.xiaohuashifu.recruit.common.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: username校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Documented
@Constraint(validatedBy = {UsernameValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Username.List.class)
public @interface Username {

    String message() default "INVALID_PARAMETER_SIZE: The size of username must be between 4 to 32.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Username[] value();
    }

}
