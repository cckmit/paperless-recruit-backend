package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;

/**
 * 描述：退款号
 *         格式：{订单号}{退款序号}
 * @see OrderNumber
 * @see RefundSerialNumber
 *
 * @author xhsf
 * @create 2021/1/7 21:50
 */
@Value
public class RefundNumber implements Domain {

    @NonNull
    OrderNumber orderNumber;

    @NonNull
    RefundSerialNumber serialNumber;

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return orderNumber.getValue() + serialNumber.getValue();
    }
}
