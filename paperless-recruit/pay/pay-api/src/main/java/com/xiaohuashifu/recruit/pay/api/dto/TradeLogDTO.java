package com.xiaohuashifu.recruit.pay.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：订单日志数据传输对象
 *
 * @author xhsf
 * @create 2021/1/8 00:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeLogDTO implements Serializable {
    private Long id;
    private String orderNumber;
    private Integer totalAmount;
    private String tradeSubject;
    private Integer expireTime;
    private String paymentMethod;
    private String qrCode;
    private String tradeNumber;
    private Integer buyerPayAmount;
    private String cancelAction;
    private String tradeStatus;
}
