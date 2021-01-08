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

    /**
     * 是否需要重试
     */
    private Boolean needRetry;

    /**
     * 关闭订单产生的行为
     * close：交易未支付，触发关闭交易动作，无退款；
     * refund：交易已支付，触发交易退款动作；
     * 未返回：未查询到交易，或接口调用失败；
     */
    private String action;

}
