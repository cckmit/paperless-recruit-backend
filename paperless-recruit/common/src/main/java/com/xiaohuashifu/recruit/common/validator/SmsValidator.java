package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.SmsConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.Sms;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: 短信内容校验器
 *  长度必须为1-70
 *
 * @author xhsf
 * @create 2019-10-09
 */
public class SmsValidator implements ConstraintValidator<Sms, String> {

    @Override
    public boolean isValid(String sms, ConstraintValidatorContext constraintValidatorContext) {
        if (sms == null) {
            return true;
        }

        return sms.length() >= 1 && sms.length() <= SmsConstants.SMS_CONTENT_MAX_LENGTH;
    }
}
