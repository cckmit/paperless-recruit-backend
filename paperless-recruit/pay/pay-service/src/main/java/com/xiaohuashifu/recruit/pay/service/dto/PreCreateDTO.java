package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：预下单数据传输对象
 *
 * @author xhsf
 * @create 2021/1/7 14:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreCreateDTO {

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 订单总金额
     */
    private Integer totalAmount;

    /**
     * 订单描述
     */
    private String subject;

    /**
     * 订单过期时间
     */
    private Integer expireTime;
}
