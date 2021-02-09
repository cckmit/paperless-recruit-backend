package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.constant.PayConstants;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：预下单请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePreCreateRequest implements Serializable {

    /**
     * 支付方式
     */
    @NotNull
    private PaymentMethodEnum paymentMethod;

    /**
     * 订单号，{7位业务号}{yyyyMMddHHmmss}{5位1秒内的自增序号} 共26位，全数字
     */
    @NotNull
    @OrderNumber
    private String orderNumber;

    /**
     * 订单总金额
     */
    @NotNull
    @Min(value = 0)
    private Integer totalAmount;

    /**
     * 订单描述
     */
    @NotBlank
    @Size(max = PayConstants.MAX_ORDER_SUBJECT_LENGTH)
    private String subject;

    /**
     * 订单过期时间
     */
    @NotNull
    @Min(value = PayConstants.MIN_TRADE_EXPIRE_TIME)
    @Max(value = PayConstants.MAX_TRADE_EXPIRE_TIME)
    private Integer expireTime;
}
