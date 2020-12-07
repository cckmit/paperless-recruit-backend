package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 手机号码校验器
 *
 * @author xhsf
 * @create 2019-10-09
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    // 准确匹配的表达式，更新于2020-11-18
//    "^((13[0-9])|(14([0-1]|[4-9]))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$"

    /**
     * 手机号码匹配模式
     */
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(PHONE_REGEX);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (s == null || s.length() == 0) {
            return true;
        }

        // 格式校验
        Matcher matcher = p.matcher(s);
        return matcher.matches();
    }

}
