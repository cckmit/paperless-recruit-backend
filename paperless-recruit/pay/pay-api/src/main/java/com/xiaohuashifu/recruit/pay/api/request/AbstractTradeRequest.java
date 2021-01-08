package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.common.request.Request;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 描述：基础订单请求
 *
 * @author xhsf
 * @create 2021/1/7 19:32
 */
@Data
@SuperBuilder
public abstract class AbstractTradeRequest implements Request, Serializable {

    /**
     * 支付方式
     */
    protected PaymentMethodEnum paymentMethod;

}
