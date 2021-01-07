package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;

/**
 * 描述：交易状态
 *
 * @author xhsf
 * @create 2021/1/7 20:21
 */
public enum TradeStatus implements Domain {

    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY,

    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    TRADE_CLOSED,

    /**
     * 交易支付成功
     */
    TRADE_SUCCESS,

    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED
}
