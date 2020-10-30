package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.Id;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 描述: id校验器
 *  必须大于等于1
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class IdValidator implements ConstraintValidator<Id, Long> {

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return true;
        }
        return id >= 1;
    }
}
