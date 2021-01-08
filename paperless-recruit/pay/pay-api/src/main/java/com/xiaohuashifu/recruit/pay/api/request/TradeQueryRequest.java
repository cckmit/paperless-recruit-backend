package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import com.xiaohuashifu.recruit.pay.api.domain.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.domain.TradeNumber;

/**
 * 描述：订单查询请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
public class TradeQueryRequest extends QueryableTradeRequest {
    public TradeQueryRequest(PaymentMethodEnum paymentMethod, OrderNumber orderNumber) {
        super(paymentMethod, orderNumber);
    }

    public TradeQueryRequest(TradeNumber tradeNumber) {
        super(tradeNumber);
    }
}
