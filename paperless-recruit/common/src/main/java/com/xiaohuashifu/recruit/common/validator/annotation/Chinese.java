package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.ChineseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 判断字符串是否是由纯中文组成的，不包含中文标点符号
 *
 * @author xhsf
 * @create 2020-11-24
 */
@Documented
@Constraint(validatedBy = {ChineseValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Chinese.List.class)
public @interface Chinese {

    String message() default "The string must be composed of Chinese.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Chinese[] value();
    }

}
