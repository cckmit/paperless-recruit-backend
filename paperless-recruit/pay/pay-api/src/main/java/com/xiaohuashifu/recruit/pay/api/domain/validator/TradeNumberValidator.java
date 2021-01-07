package com.xiaohuashifu.recruit.pay.api.domain.validator;

import javax.validation.ValidationException;

/**
 * 描述：支付平台的交易号校验器
 *
 * @author xhsf
 * @create 2021/1/7 19:09
 */
public interface TradeNumberValidator {

    /**
     * 检验是否符合所要求格式
     */
    void validate(String tradeNumber) throws ValidationException;

}
