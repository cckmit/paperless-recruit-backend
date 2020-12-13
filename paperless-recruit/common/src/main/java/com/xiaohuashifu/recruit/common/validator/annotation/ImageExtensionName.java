package com.xiaohuashifu.recruit.common.validator.annotation;

import com.xiaohuashifu.recruit.common.validator.ImageExtensionNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 描述: 图片扩展名校验器
 *          默认支持 [.jpg | .jpeg | .png | .gif]
 *
 * @author xhsf
 * @create 2020-12-13
 */
@Documented
@Constraint(validatedBy = {ImageExtensionNameValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ImageExtensionName.List.class)
public @interface ImageExtensionName {

    String message() default "Unsupported image extension name.";

    /**
     * 支持的图片扩展名
     */
    String[] supportedImageExtensionNames() default {".jpg", ".jpeg", ".png", ".gif"};

    /**
     * 额外支持的图片扩展名
     */
    String[] additionalSupportImageExtensionNames() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
            ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ImageExtensionName[] value();
    }

}
