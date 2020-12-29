package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.constant.StudentNumberConstants;
import com.xiaohuashifu.recruit.common.validator.StudentNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 学号校验器校验器
 *       必须满足正则表达式 StudentNumberConstants.STUDENT_NUMBER_REGEXP
 * @see StudentNumberConstants
 *
 * @author xhsf
 * @create 2020-10-09
 */
@Documented
@Constraint(validatedBy = {StudentNumberValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StudentNumber.List.class)
public @interface StudentNumber {

    String message() default "The studentNumber format error.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        StudentNumber[] value();
    }

}
