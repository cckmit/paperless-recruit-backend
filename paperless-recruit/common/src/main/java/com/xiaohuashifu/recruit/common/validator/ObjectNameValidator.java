package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.ObjectConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.ObjectName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

/**
 * 描述: 对象名校验器校验，对象名类似于 users/avatars/321.jpg
 *          在使用 UTF-8 编码后长度必须在 1-768 字节之间，同时不能以 '/' 开头。
 *
 * @author xhsf
 * @create 2020-12-07
 */
public class ObjectNameValidator implements ConstraintValidator<ObjectName, String> {

    @Override
    public boolean isValid(String objectName, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (objectName == null || objectName.length() == 0) {
            return true;
        }

        // 不能以 '/' 开头
        if (objectName.charAt(0) == '/') {
            return false;
        }

        // 以 UTF-8 编码格式的长度在 1-768 之间
        // 这里不需要检验长度为 0 的情况，因为入参长度为 0 的我们不进行校验
        return objectName.getBytes(StandardCharsets.UTF_8).length <= ObjectConstants.MAX_OBJECT_NAME_LENGTH;
    }
}
