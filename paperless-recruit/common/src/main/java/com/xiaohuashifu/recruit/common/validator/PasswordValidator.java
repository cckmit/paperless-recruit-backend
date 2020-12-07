package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.PasswordConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: password 校验器
 *  密码长度必须在 6~20 位之间
 *
 * @author xhsf
 * @create 2019-10-09
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (password == null || password.length() == 0) {
            return true;
        }

        // 格式校验
        return password.length() >= PasswordConstants.PASSWORD_MIN_LENGTH
                && password.length() <= PasswordConstants.PASSWORD_MAX_LENGTH;
    }
}
