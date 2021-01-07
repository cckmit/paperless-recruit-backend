package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.domain.PaymentMethod;
import com.xiaohuashifu.recruit.pay.api.domain.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.domain.TradeNumber;
import lombok.Getter;
import lombok.NonNull;

/**
 * 描述：可查询的订单请求
 *
 * @author xhsf
 * @create 2021/1/7 19:32
 */
@Getter
public abstract class QueryableTradeRequest extends AbstractTradeRequest {

    /**
     * 业务号
     */
    private final OrderNumber orderNumber;

    /**
     * 支付平台产生的订单号
     */
    private final TradeNumber tradeNumber;

    public QueryableTradeRequest(PaymentMethod paymentMethod, @NonNull OrderNumber orderNumber) {
        super(paymentMethod);
        this.orderNumber = orderNumber;
        tradeNumber = null;
    }

    public QueryableTradeRequest(@NonNull TradeNumber tradeNumber) {
        super(tradeNumber.getPaymentMethod());
        this.tradeNumber = tradeNumber;
        this.orderNumber = null;
    }
}
