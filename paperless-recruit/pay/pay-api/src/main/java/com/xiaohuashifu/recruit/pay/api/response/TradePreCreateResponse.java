package com.xiaohuashifu.recruit.pay.api.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：预下单响应
 *
 * @author xhsf
 * @create 2021/1/7 16:55
 */
@Data
@Builder
public class TradePreCreateResponse implements Serializable {

    /**
     * 二维码
     */
    private final String qrCode;

    /**
     * 订单日志编号
     */
    private final Long tradeLogId;

}
