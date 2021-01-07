package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 描述：预下单数据传输对象
 *
 * @author xhsf
 * @create 2021/1/7 14:19
 */
@Data
@Builder
public class PreCreateDTO {

    private String orderNumber;

    private Integer totalAmount;

    private String description;

    private Integer expireTime;
}
