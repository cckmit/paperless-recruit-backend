package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.FullNameConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.FullName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 姓名校验器
 *      必须长度在FullNameConstants.MIN_FULL_NAME_LENGTH - FullNameConstants.MAX_FULL_NAME_LENGTH 之间
 * @see FullNameConstants
 *
 * @author xhsf
 * @create 2020-10-09
 */
public class FullNameValidator implements ConstraintValidator<FullName, String> {

    @Override
    public boolean isValid(String fullName, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (fullName == null || fullName.length() == 0) {
            return true;
        }

        return fullName.length() >= FullNameConstants.MIN_FULL_NAME_LENGTH
                && fullName.length() <= FullNameConstants.MAX_FULL_NAME_LENGTH;
    }

}
