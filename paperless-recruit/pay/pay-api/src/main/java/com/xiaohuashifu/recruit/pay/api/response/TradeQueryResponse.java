package com.xiaohuashifu.recruit.pay.api.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：订单查询响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
public class TradeQueryResponse implements Serializable {

    /**
     * 平台订单号
     */
    private String tradeNumber;

    /**
     * 买家付款金额
     */
    private Integer buyerPayAmount;

    /**
     * 总金额
     */
    private Integer totalAmount;

    /**
     * 订单状态
     */
    private String tradeStatus;

}
