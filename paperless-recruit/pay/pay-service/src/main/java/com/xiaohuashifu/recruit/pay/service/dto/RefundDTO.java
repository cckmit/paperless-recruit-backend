package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 描述：退款的数据传输对象
 *
 * @author xhsf
 * @create 2021/1/8 16:46
 */
@Data
@Builder
public class RefundDTO {

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 交易号，支付平台产生
     */
    private String tradeNumber;

    /**
     * 退款号
     */
    private String refundNumber;

    /**
     * 退款金额
     */
    private Integer refundAmount;
}
