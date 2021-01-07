package com.xiaohuashifu.recruit.pay.api.domain;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 描述：退款序号
 *    格式 3 位整型
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
public class RefundSerialNumber extends SerialNumber {

    /**
     * 最大序号长度
     */
    public static final int MAX_ORDER_SERIAL_NUMBER_LENGTH = 3;

    @Override
    protected void validate(String serialNumber) {
        if (serialNumber.length() != MAX_ORDER_SERIAL_NUMBER_LENGTH) {
            throw new ValidationException();
        }
        if (!StringUtils.isNumeric(serialNumber)) {
            throw new ValidationException();
        }
    }

    public RefundSerialNumber(String serialNumber) {
        super(serialNumber);
    }

}
