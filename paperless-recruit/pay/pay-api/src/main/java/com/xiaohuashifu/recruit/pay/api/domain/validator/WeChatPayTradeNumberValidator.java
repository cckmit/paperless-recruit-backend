package com.xiaohuashifu.recruit.pay.api.domain.validator;

import javax.validation.ValidationException;

/**
 * 描述：微信支付的交易号校验器
 *
 * @author xhsf
 * @create 2021/1/7 19:09
 */
public class WeChatPayTradeNumberValidator implements TradeNumberValidator {

    /**
     * 最小订单号长度
     */
    public static final int MIN_TRADE_NUMBER_LENGTH = 1;

    /**
     * 最大订单号长度
     */
    public static final int MAX_TRADE_NUMBER_LENGTH = 32;

    /**
     * 检验是否符合所要求格式
     */
    @Override
    public void validate(String tradeNumber) {
        if (tradeNumber.length() < MIN_TRADE_NUMBER_LENGTH || tradeNumber.length() > MAX_TRADE_NUMBER_LENGTH) {
            throw new ValidationException();
        }
    }

}
