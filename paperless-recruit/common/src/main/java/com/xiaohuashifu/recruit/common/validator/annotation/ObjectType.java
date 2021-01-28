package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.ObjectTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 对象类型校验器
 *          默认支持 [.jpg | .jpeg | .png | .gif]
 *
 * @author xhsf
 * @create 2021-1-29
 */
@Documented
@Constraint(validatedBy = {ObjectTypeValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ObjectType.List.class)
public @interface ObjectType {

    String message() default "Unsupported object type.";

    /**
     * 支持的对象类型
     */
    String[] supportedObjectTypes() default {".jpg", ".jpeg", ".png", ".gif"};

    /**
     * 额外支持的对象类型
     */
    String[] additionalSupportedObjectTypes() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ObjectType[] value();
    }

}
