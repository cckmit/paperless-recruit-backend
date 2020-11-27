package com.xiaohuashifu.recruit.common.validator;


import com.xiaohuashifu.recruit.common.validator.annotation.AntPath;
import org.springframework.util.AntPathMatcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: AntPath校验器，必须符合Ant格式
 *
 * @author xhsf
 * @email 827032783@qq.com
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
