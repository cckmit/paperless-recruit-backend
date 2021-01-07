package com.xiaohuashifu.recruit.pay.api.response;

import com.xiaohuashifu.recruit.common.response.Response;
import com.xiaohuashifu.recruit.pay.api.domain.Money;
import lombok.Builder;
import lombok.Data;

/**
 * 描述：订单退款响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
public class TradeRefundResponse implements Response {

    /**
     * 本次退款是否发生了资金变化
     */
    private final Boolean fundChange;

    /**
     * 该笔交易已退款的总金额
     */
    private final Money refundFee;

}
