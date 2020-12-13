package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 用户名校验器
 * 用户名不能是手机和邮箱的格式
 *  用户名必须满足长度在 4-32 之间，只包含数字、小写字母、'-'、'_'，且以字母开头
 *
 * @author xhsf
 * @create 2020-10-09
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final PhoneValidator phoneValidator = new PhoneValidator();

    private static final EmailValidator emailValidator = new EmailValidator();

    /**
     * 用户名匹配模式
     */
    private static final String USERNAME_REGEX = "^([a-z])([a-z]|\\d|_|-){3,31}$";

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(USERNAME_REGEX);

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (username == null || username.length() == 0) {
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

        // 格式校验
        Matcher matcher = p.matcher(username);
        return matcher.matches();
    }

}
