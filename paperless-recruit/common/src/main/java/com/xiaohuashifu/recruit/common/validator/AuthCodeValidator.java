package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 通用验证码校验器
 *  长度必须为6，且由数字组成
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class AuthCodeValidator implements ConstraintValidator<AuthCode, String> {

    @Override
    public boolean isValid(String authCode, ConstraintValidatorContext constraintValidatorContext) {
        if (authCode == null) {
            return true;
        }

        return authCode.length() == 6 && StringUtils.isNumeric(authCode);
    }
}
