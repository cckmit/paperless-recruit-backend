package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.NotAllFieldsNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 校验一个对象是否所有域都为空
 *
 * @author xhsf
 * @create 2021-2-1
 */
@Documented
@Constraint(validatedBy = {NotAllFieldsNullValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotAllFieldsNull.List.class)
public @interface NotAllFieldsNull {

    String message() default "Not all fields is null.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        NotAllFieldsNull[] value();
    }

}
