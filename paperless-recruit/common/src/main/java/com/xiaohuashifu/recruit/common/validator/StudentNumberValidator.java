package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.constant.StudentNumberConstants;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 学号校验器校验器
 *     必须满足正则表达式 StudentNumberConstants.STUDENT_NUMBER_REGEXP
 * @see StudentNumberConstants
 *
 * @author xhsf
 * @create 2020-10-09
 */
public class StudentNumberValidator implements ConstraintValidator<StudentNumber, String> {

    /**
     * 构造静态的匹配模式
     */
    private static final Pattern p = Pattern.compile(StudentNumberConstants.STUDENT_NUMBER_REGEXP);

    @Override
    public boolean isValid(String studentNumber, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (studentNumber == null || studentNumber.length() == 0) {
            return true;
        }

        // 格式校验
        Matcher matcher = p.matcher(studentNumber);
        return matcher.matches();
    }

}
