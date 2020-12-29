package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.constant.FullNameConstants;
import com.xiaohuashifu.recruit.common.validator.FullNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 姓名校验器
 *       必须长度在FullNameConstants.MIN_FULL_NAME_LENGTH - FullNameConstants.MAX_FULL_NAME_LENGTH 之间
 * @see FullNameConstants
 *
 * @author xhsf
 * @create 2020-10-09
 */
@Documented
@Constraint(validatedBy = {FullNameValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FullName.List.class)
public @interface FullName {

    String message() default "The length of fullName must be between " + FullNameConstants.MIN_FULL_NAME_LENGTH
            + " and " + FullNameConstants.MAX_FULL_NAME_LENGTH + ".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FullName[] value();
    }

}
