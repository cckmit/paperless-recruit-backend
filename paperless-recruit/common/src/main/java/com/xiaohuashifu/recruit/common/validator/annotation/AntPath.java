package com.xiaohuashifu.recruit.common.validator.annotation;


import com.xiaohuashifu.recruit.common.validator.AntPathValidator;
import com.xiaohuashifu.recruit.common.validator.UrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: AntPath校验 判断一个url是否是ant格式的
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
@Documented
@Constraint(validatedBy = {AntPathValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AntPath.List.class)
public @interface AntPath {

    String message() default "The path not meet the ant format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        AntPath[] value();
    }

}
