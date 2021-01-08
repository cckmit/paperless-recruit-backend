package com.xiaohuashifu.recruit.pay.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderNumber implements Serializable {

    private BusinessNumber businessNumber;

    private CompactTime time;

    private OrderSerialNumber serialNumber;

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return businessNumber.getValue() + time.getValue() + serialNumber.getValue();
    }

}
