package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.SmsAuthCode;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 短信验证码校验器
 *  长度必须为6，且由数字组成
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class SmsAuthCodeValidator implements ConstraintValidator<SmsAuthCode, String> {

    @Override
    public boolean isValid(String messageAuthCode, ConstraintValidatorContext constraintValidatorContext) {
        if (messageAuthCode == null) {
            return true;
        }

        return messageAuthCode.length() == 6 && StringUtils.isNumeric(messageAuthCode);
    }
}
