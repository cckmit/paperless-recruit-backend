package com.xiaohuashifu.recruit.common.validator.annotation;


import com.xiaohuashifu.recruit.common.validator.UrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: URL链接校验
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Documented
@Constraint(validatedBy = {UrlValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Url.List.class)
public @interface Url {

    String message() default "INVALID_PARAMETER: The parameter of url is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Url[] value();
    }

}
