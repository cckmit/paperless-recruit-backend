package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import com.xiaohuashifu.recruit.pay.api.domain.validator.AlipayTradeNumberValidator;
import com.xiaohuashifu.recruit.pay.api.domain.validator.TradeNumberValidator;
import com.xiaohuashifu.recruit.pay.api.domain.validator.WeChatPayTradeNumberValidator;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：支付平台的订单号
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Value
public class TradeNumber implements Domain {

    private static final Map<PaymentMethodEnum, TradeNumberValidator> VALIDATOR_MAP = new HashMap<>();

    static {
        VALIDATOR_MAP.put(PaymentMethodEnum.ALIPAY, new AlipayTradeNumberValidator());
        VALIDATOR_MAP.put(PaymentMethodEnum.WECHAT_PAY, new WeChatPayTradeNumberValidator());
    }

    String tradeNumber;

    PaymentMethodEnum paymentMethod;

    public TradeNumber(@NonNull String tradeNumber, @NonNull PaymentMethodEnum paymentMethod) {
        VALIDATOR_MAP.get(paymentMethod).validate(tradeNumber);
        this.tradeNumber = tradeNumber;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return getTradeNumber();
    }

}
