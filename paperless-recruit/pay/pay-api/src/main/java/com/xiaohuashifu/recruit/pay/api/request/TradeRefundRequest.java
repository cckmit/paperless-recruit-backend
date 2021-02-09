package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：订单退款请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRefundRequest implements Serializable {

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
     * 退款金额
     */
    @NotNull
    @Min(value = 0)
    private Integer refundAmount;

}
