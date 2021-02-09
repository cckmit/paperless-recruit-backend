package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：订单查询结果数据传输对象
 *
 * @author xhsf
 * @create 2021/1/7 14:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryResultDTO {

    /**
     * 平台订单号
     */
    private String tradeNumber;

    /**
     * 买家实付金额
     */
    private Integer buyerPayAmount;

    /**
     * 订单总金额
     */
    private Integer totalAmount;

    /**
     * 订单状态
     * WAIT_BUYER_PAY（交易创建，等待买家付款）
     * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
     * TRADE_SUCCESS（交易支付成功）
     * TRADE_FINISHED（交易结束，不可退款）
     */
    private String tradeStatus;

}
