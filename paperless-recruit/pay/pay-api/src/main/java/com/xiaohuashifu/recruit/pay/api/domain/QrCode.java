package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;

import javax.validation.ValidationException;

/**
 * 描述：二维码
 *
 * @author xhsf
 * @create 2021/1/7 20:12
 */
@Value
public class QrCode implements Domain {

    /**
     * 二维码最小长度
     */
    public static final int MIN_QR_CODE_LENGTH = 1;

    /**
     * 二维码最大长度
     */
    public static final int MAX_QR_CODE_LENGTH = 1024;

    String value;

    public QrCode(@NonNull String qrCode) {
        if (qrCode.length() < MIN_QR_CODE_LENGTH || qrCode.length() > MAX_QR_CODE_LENGTH) {
            throw new ValidationException();
        }
        this.value = qrCode;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
