package com.xiaohuashifu.recruit.pay.api.constant;

/**
 * 描述：撤销订单时的行为
 *
 * @author xhsf
 * @create 2021/1/7 20:40
 */
public enum CancelActionEnum {

    /**
     * 交易未支付，触发关闭交易动作，无退款
     */
    CLOSE,

    /**
     * 交易已支付，触发交易退款动作
     */
    REFUND

}
