package com.xiaohuashifu.recruit.pay.service.do0;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述：订单日志数据对象
 *
 * @author xhsf
 * @create 2021/1/8 00:35
 */
@Data
@Builder
public class TradeLogDO {
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
