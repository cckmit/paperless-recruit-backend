package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 描述：退款结果数据传输对象
 *
 * @author xhsf
 * @create 2021/1/7 15:23
 */
@Builder
@Data
public class RefundResultDTO {

    /**
     * 退款总金额
     */
    private Integer refundFee;

    /**
     * 本次退款是否发生了资金变化
     */
    private Boolean fundChange;
}
