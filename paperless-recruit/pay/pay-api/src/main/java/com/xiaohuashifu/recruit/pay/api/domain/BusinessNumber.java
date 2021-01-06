package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 描述：业务号
 *      格式：7位数字，范围是 0000000-9999999
 *
 * @author xhsf
 * @create 2021/1/7 01:21
 */
@Value
public class BusinessNumber implements Domain {

    /**
     * 最小业务号值
     */
    private static final int MIN_BUSINESS_NUMBER_VALUE = 0;

    /**
     * 最大业务号值
     */
    private static final int MAX_BUSINESS_NUMBER_VALUE = 9999999;

    String value;

    public BusinessNumber(@NonNull String value) {
        if (!StringUtils.isNumeric(value)) {
            throw new ValidationException();
        }
        this.value = value;
    }

    public BusinessNumber(int value) {
        if (value < MIN_BUSINESS_NUMBER_VALUE || value > MAX_BUSINESS_NUMBER_VALUE) {
            throw new ValidationException();
        }
        this.value = String.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
