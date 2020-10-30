package com.xiaohuashifu.recruit.common.validator.annotation;


import com.xiaohuashifu.recruit.common.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: password校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Password.List.class)
public @interface Password {

    String message() default "INVALID_PARAMETER_SIZE: The size of password must be between 6 to 20.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Password[] value();
    }

}
