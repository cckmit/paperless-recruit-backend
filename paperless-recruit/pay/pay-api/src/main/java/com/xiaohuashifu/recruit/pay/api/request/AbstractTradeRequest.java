package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.common.request.Request;
import com.xiaohuashifu.recruit.pay.api.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * 描述：基础订单请求
 *
 * @author xhsf
 * @create 2021/1/7 19:32
 */
@Getter
@AllArgsConstructor
public abstract class AbstractTradeRequest implements Request {

    /**
     * 支付方式
     */
    @NonNull
    private final PaymentMethod paymentMethod;

}
