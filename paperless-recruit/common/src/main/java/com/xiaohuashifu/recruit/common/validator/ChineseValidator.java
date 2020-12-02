package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.Chinese;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 判断字符串是否是由纯中文组成的，不包含中文标点符号
 *
 * @author xhsf
 * @create 2020-11-24
 */
public class ChineseValidator implements ConstraintValidator<Chinese, String> {

    /**
     * 中文匹配模式
     */
    private static final String CHINESE_REGEX = "^[\\u4E00-\\u9FA5]+$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(CHINESE_REGEX);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        Matcher matcher = p.matcher(s);
        return matcher.matches();
    }

}
