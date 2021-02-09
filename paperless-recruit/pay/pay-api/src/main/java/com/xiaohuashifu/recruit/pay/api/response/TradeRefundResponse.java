package com.xiaohuashifu.recruit.pay.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：订单退款响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRefundResponse implements Serializable {

    /**
     * 本次退款是否发生了资金变化
     */
    private Boolean fundChange;

    /**
     * 该笔交易已退款的总金额
     */
    private Integer refundFee;

}
