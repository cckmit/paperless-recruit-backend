package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.ObjectNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 对象名校验器校验，对象名类似于 /users/avatars/321.jpg
 *          在使用 UTF-8 编码后长度必须在 1-1023 字节之间，同时不能以 '\' 开头。
 *
 * @author xhsf
 * @create 2020-12-07
 */
@Documented
@Constraint(validatedBy = {ObjectNameValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ObjectName.List.class)
public @interface ObjectName {

    String message() default "The length of object name must be between 1 and 1023, and can't start with \\.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ObjectName[] value();
    }

}
