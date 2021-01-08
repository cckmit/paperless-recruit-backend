package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.OrderNumber;
import com.xiaohuashifu.recruit.pay.api.constant.PayConstants;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import lombok.Builder;
import lombok.Data;

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
public class TradePreCreateRequest implements Serializable {

    @NotNull(message = "The paymentMethod can't be null.")
    private PaymentMethodEnum paymentMethod;

    /**
     * 订单号，{7位业务号}{yyyyMMddHHmmss}{5位1秒内的自增序号} 共26位，全数字
     */
    @NotNull(message = "The orderNumber can't be null.")
    @OrderNumber
    private String orderNumber;

    /**
     * 订单总金额
     */
    @NotNull(message = "The totalAmount can't be null.")
    @Min(value = 0, message = "The totalAmount must be greater than 0.")
    private Integer totalAmount;

    /**
     * 订单描述
     */
    @NotBlank(message = "The subject can't be blank.")
    @Size(max = PayConstants.MAX_ORDER_SUBJECT_LENGTH,
            message = "The length of subject must not be greater than "
                    + PayConstants.MAX_ORDER_SUBJECT_LENGTH + ".")
    private String subject;

    /**
     * 订单过期时间
     */
    @NotNull(message = "The expireTime can't be null.")
    @Min(value = PayConstants.MIN_TRADE_EXPIRE_TIME,
            message = "The expireTime must not be less than " + PayConstants.MIN_TRADE_EXPIRE_TIME + ".")
    @Max(value = PayConstants.MAX_TRADE_EXPIRE_TIME,
            message = "The expireTime must not be greater than " + PayConstants.MAX_TRADE_EXPIRE_TIME + ".")
    private Integer expireTime;
}
