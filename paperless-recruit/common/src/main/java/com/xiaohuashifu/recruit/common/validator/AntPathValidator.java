package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.AntPath;
import org.springframework.util.AntPathMatcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: AntPath 校验器，必须符合 Ant 格式
 *
 * @author xhsf
 * @create 2019-10-09
 */
public class AntPathValidator implements ConstraintValidator<AntPath, String> {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean isValid(String path, ConstraintValidatorContext constraintValidatorContext) {
        if (path == null) {
            return true;
        }
        return antPathMatcher.isPattern(path);
    }

}
