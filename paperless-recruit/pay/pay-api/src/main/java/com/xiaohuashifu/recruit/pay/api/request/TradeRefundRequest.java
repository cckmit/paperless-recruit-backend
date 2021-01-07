package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.domain.*;
import lombok.NonNull;

/**
 * 描述：订单退款请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
public class TradeRefundRequest extends QueryableTradeRequest {

    /**
     * 退款金额
     */
    private final Money refundAmount;

    /**
     * 退款号
     */
    private final RefundNumber refundNumber;

    public TradeRefundRequest(PaymentMethod paymentMethod, OrderNumber orderNumber, @NonNull Money refundAmount) {
        super(paymentMethod, orderNumber);
        this.refundAmount = refundAmount;
        this.refundNumber = null;
    }

    public TradeRefundRequest(TradeNumber tradeNumber, @NonNull Money refundAmount) {
        super(tradeNumber);
        this.refundAmount = refundAmount;
        this.refundNumber = null;
    }

    public TradeRefundRequest(PaymentMethod paymentMethod, OrderNumber orderNumber, @NonNull Money refundAmount,
                              @NonNull RefundNumber refundNumber) {
        super(paymentMethod, orderNumber);
        this.refundAmount = refundAmount;
        this.refundNumber = refundNumber;
    }

    public TradeRefundRequest(TradeNumber tradeNumber, @NonNull Money refundAmount,
                              @NonNull RefundNumber refundNumber) {
        super(tradeNumber);
        this.refundAmount = refundAmount;
        this.refundNumber = refundNumber;
    }

}
