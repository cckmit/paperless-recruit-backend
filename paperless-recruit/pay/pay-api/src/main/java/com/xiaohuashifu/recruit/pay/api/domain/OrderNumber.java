package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.Value;

/**
 * 描述：订单号
 *    格式 {7位业务号}{YYYYMMDDHHmmss}{5位1秒内的自增序号} 共26位
 *    @see BusinessNumber 业务号：7位数字
 *    时间：YYYYMMDDHHmmss
 *    序号：5位整型
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Value
public class OrderNumber implements Domain {

    BusinessNumber businessNumber;



}
