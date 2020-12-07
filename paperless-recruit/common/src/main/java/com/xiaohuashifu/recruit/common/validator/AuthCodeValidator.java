package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.AuthCodeConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 通用验证码校验器
 *  长度必须为6，且由数字组成
 *
 * @author xhsf
 * @create 2019-10-09
 */
public class AuthCodeValidator implements ConstraintValidator<AuthCode, String> {

    @Override
    public boolean isValid(String authCode, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (authCode == null || authCode.length() == 0) {
            return true;
        }

        // 长度必须为6，且由数字组成
        return authCode.length() == AuthCodeConstants.AUTH_CODE_LENGTH && StringUtils.isNumeric(authCode);
    }
}
