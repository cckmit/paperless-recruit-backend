package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;

/**
 * 描述：订单号
 *    格式 {7位业务号}{yyyyMMddHHmmss}{5位1秒内的自增序号} 共26位
 *    @see BusinessNumber 业务号：7位数字
 *    @see CompactTime 时间：yyyyMMddHHmmss
 *    @see OrderSerialNumber 序号：5位整型
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Value
public class OrderNumber implements Domain {

    @NonNull
    BusinessNumber businessNumber;

    @NonNull
    CompactTime time;

    @NonNull
    OrderSerialNumber serialNumber;

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return businessNumber.getValue() + time.getValue() + serialNumber.getValue();
    }

}
