package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 用户名校验器
 * 用户名不能是手机和邮箱的格式
 *  用户名长度必须在4~32位之间
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final PhoneValidator phoneValidator = new PhoneValidator();
    private static final EmailValidator emailValidator = new EmailValidator();


    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return true;
        }

        // 用户名不能是手机号
        if (phoneValidator.isValid(username, constraintValidatorContext)) {
            return false;
        }

        // 用户名不能是邮箱
        if (emailValidator.isValid(username, constraintValidatorContext)) {
            return false;
        }
        return username.length() >= 4 && username.length() <= 32;
    }

}
