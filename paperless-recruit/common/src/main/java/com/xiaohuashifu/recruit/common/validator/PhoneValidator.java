package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// FIXME: 2019/10/9 这个正则表达式有问题，要修改

/**
 * 描述: 手机号码校验器
 *  必须符合正则表达式 ^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[0-9])|(17[013678])|(18[0-9])|(19[0-9]))\d{8}$
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    /**
     * 手机号码匹配模式
     */
    private static final String REGEX_PHONE =
            "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[0-9])|(17[013678])|(18[0-9])|(19[0-9]))\\d{8}$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(REGEX_PHONE);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        Matcher matcher = p.matcher(s);
        return matcher.matches();
    }
}
