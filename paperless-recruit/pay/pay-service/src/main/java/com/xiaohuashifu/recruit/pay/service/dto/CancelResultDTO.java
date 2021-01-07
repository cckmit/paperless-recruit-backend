package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 描述：关闭订单结果
 *
 * @author xhsf
 * @create 2021/1/7 15:10
 */
@Data
@Builder
public class CancelResultDTO {

    private Boolean needRetry;

    private String action;

}
