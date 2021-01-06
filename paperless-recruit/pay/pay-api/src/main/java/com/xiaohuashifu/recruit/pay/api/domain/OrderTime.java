package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ValidationException;

/**
 * 描述：订单时间
 *          格式 {YYYYMMDDHHmmss}
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Value
public class OrderTime implements Domain {

    String value;

    public OrderTime(@NonNull String value) {
        

        if (!StringUtils.isNumeric(value)) {
            throw new ValidationException();
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
