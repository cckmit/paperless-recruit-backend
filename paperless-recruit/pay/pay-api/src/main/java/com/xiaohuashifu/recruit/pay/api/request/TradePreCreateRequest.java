package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.domain.PaymentMethod;
import com.xiaohuashifu.recruit.pay.api.domain.Money;
import com.xiaohuashifu.recruit.pay.api.domain.TradeExpireTime;
import com.xiaohuashifu.recruit.pay.api.domain.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.domain.TradeSubject;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * 描述：预下单请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
@Getter
@ToString
public class TradePreCreateRequest extends AbstractTradeRequest {

    /**
     * 业务号
     */
    private final OrderNumber orderNumber;

    /**
     * 订单总金额
     */
    private final Money totalAmount;

    /**
     * 订单描述
     */
    private final TradeSubject subject;

    /**
     * 订单过期时间
     */
    private final TradeExpireTime expireTime;

    public TradePreCreateRequest(PaymentMethod paymentMethod, @NonNull OrderNumber orderNumber,
                                 @NonNull Money totalAmount, @NonNull TradeSubject subject,
                                 @NonNull TradeExpireTime expireTime) {
        super(paymentMethod);
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.subject = subject;
        this.expireTime = expireTime;
    }
}
