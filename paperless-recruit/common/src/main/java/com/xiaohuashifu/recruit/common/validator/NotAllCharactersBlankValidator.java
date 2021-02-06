package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 校验一个字符串是否所有字符都为空
 *
 * @author xhsf
 * @create 2021-2-1
 */
public class NotAllCharactersBlankValidator implements ConstraintValidator<NotAllCharactersBlank, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查 null 的情况
        if (s == null) {
            return true;
        }

        // 判断字符串是否所有字符都为空
        return s.trim().length() > 0;
    }

}
