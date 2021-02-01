package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 校验一个对象是否所有域都为空
 *
 * @author xhsf
 * @create 2021-2-1
 */
public class NotAllFieldsNullValidator implements ConstraintValidator<NotAllFieldsNull, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(o);
        // 不检查 null 的情况
        if (o == null) {
            return true;
        }

        // 判断对象是否所有域都为 null
        return !ObjectUtils.allFieldIsNull(o);
    }

}
