package com.xiaohuashifu.recruit.pay.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;
import java.io.Serializable;

/**
 * 描述：业务号
 *      格式：7位数字，范围是 0000000-9999999
 *
 * @author xhsf
 * @create 2021/1/7 01:21
 */
@Data
@NoArgsConstructor
public class BusinessNumber implements Serializable {

    /**
     * 最小业务号值
     */
    public static final int MIN_BUSINESS_NUMBER_VALUE = 0;

    /**
     * 最大业务号值
     */
    public static final int MAX_BUSINESS_NUMBER_VALUE = 9999999;

    /**
     * 最大业务号长度
     */
    public static final int BUSINESS_NUMBER_LENGTH = 7;

    private String value;

    public BusinessNumber(String businessNumber) {
        if (businessNumber.length() != BUSINESS_NUMBER_LENGTH) {
            throw new ValidationException();
        }
        if (!StringUtils.isNumeric(businessNumber)) {
            throw new ValidationException();
        }
        this.value = businessNumber;
    }

    public BusinessNumber(int businessNumber) {
        if (businessNumber < MIN_BUSINESS_NUMBER_VALUE || businessNumber > MAX_BUSINESS_NUMBER_VALUE) {
            throw new ValidationException();
        }
        this.value = String.format("%0" + BUSINESS_NUMBER_LENGTH + "d", businessNumber);
    }

    @Override
    public String toString() {
        return getValue();
    }

    public int getIntValue() {
        return Integer.parseInt(getValue());
    }

}
