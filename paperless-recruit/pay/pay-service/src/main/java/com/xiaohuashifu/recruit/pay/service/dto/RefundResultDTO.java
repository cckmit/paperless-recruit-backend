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

    private Integer refundFee;

    private Boolean fundChange;
}
