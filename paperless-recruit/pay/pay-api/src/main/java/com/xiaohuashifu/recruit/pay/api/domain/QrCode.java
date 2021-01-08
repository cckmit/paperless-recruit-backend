package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.validation.ValidationException;
import java.io.Serializable;

/**
 * 描述：二维码
 *
 * @author xhsf
 * @create 2021/1/7 20:12
 */
@Data
@NoArgsConstructor
public class QrCode implements Serializable {

    /**
     * 二维码最小长度
     */
    public static final int MIN_QR_CODE_LENGTH = 1;

    /**
     * 二维码最大长度
     */
    public static final int MAX_QR_CODE_LENGTH = 1024;

    private String value;

    public QrCode(String qrCode) {
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
