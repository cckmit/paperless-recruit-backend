package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 用户名校验器
 *  用户名长度必须在4~32位之间
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return true;
        }
        return username.length() >= 4 && username.length() <= 32;
    }
}
