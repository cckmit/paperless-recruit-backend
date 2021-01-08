package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import com.xiaohuashifu.recruit.pay.api.domain.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.domain.TradeNumber;

/**
 * 描述：订单撤销请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
public class TradeCancelRequest extends QueryableTradeRequest {
    public TradeCancelRequest(PaymentMethodEnum paymentMethod, OrderNumber orderNumber) {
        super(paymentMethod, orderNumber);
    }

    public TradeCancelRequest(TradeNumber tradeNumber) {
        super(tradeNumber);
    }
}
