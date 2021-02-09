package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 描述: 时间校验
 *
 * @author xhsf
 * @create 2021-2-9
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LocalDateTime min;

    private LocalDateTime max;

    private DateTime dateTime;

    @Override
    public void initialize(DateTime dateTime) {
        this.min = LocalDateTime.parse(dateTime.min(), DATE_TIME_FORMATTER);
        this.max = LocalDateTime.parse(dateTime.max(), DATE_TIME_FORMATTER);
        this.dateTime = dateTime;
    }

    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (dateTime == null) {
            return true;
        }

        // 通过校验
        if (dateTime.isAfter(min) || dateTime.isEqual(min) || dateTime.isBefore(max) || dateTime.equals(max)) {
            return true;
        }

        // 未通过校验
        addViolationMessage(constraintValidatorContext);
        return false;
    }

    /**
     * 添加校验错误提示信息
     *
     * @param constraintValidatorContext ConstraintValidatorContext
     */
    private void addViolationMessage(ConstraintValidatorContext constraintValidatorContext) {
        // 添加提示信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                MessageFormat.format(dateTime.message(), dateTime.min(), dateTime.max()))
                .addConstraintViolation();
    }

}
