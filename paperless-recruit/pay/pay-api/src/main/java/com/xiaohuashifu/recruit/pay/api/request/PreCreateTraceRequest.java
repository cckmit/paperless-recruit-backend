package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.constant.PayConstants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述：预下单请求
 *
 * @author xhsf
 * @create 2021/1/6 21:36
 */
@Data
@Builder
public class PreCreateTraceRequest {


    /**
     * 业务号，推荐 就会被你
     */
    @NotBlank(message = "The businessNumber can't be blank.")
    @Size(min = PayConstants.MIN_BUSINESS_NUMBER_LENGTH, max = PayConstants.MAX_BUSINESS_NUMBER_LENGTH,
            message = "The length of businessNumber must be between " + PayConstants.MIN_BUSINESS_NUMBER_LENGTH
                    + " and " + PayConstants.MAX_BUSINESS_NUMBER_LENGTH + ".")
    private String businessNumber;

    /**
     * 订单总金额，单位分
     */
    @NotNull(message = "The total can't be null.")
    @Min(value = 0, message = "The total can't be less than 0.")
    private Integer total;

    /**
     * 订单描述
     */
    @NotBlank(message = "The description can't be blank.")
    @Size(max = PayConstants.MAX_DESCRIPTION_LENGTH, message = "The length of description must not be greater than "
            + PayConstants.MAX_DESCRIPTION_LENGTH + ".")
    private String description;

    /**
     * 订单过期时间
     */
    @NotNull(message = "The expireTime can't be null.")
    @Min(value = PayConstants.MIN_EXPIRE_TIME, message = "The expireTime can't be less than "
            + PayConstants.MIN_EXPIRE_TIME + ".")
    @Min(value = PayConstants.MAX_EXPIRE_TIME, message = "The expireTime can't be greater than "
            + PayConstants.MAX_EXPIRE_TIME + ".")
    private Integer expireTime;

}
