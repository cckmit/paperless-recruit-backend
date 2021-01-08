package com.xiaohuashifu.recruit.pay.api.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：订单撤销响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
public class TradeCancelResponse implements Serializable {

    /**
     * 撤销订单时的行为
     */
    private String action;

}
