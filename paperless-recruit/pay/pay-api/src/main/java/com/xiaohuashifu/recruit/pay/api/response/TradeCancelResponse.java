package com.xiaohuashifu.recruit.pay.api.response;

import com.xiaohuashifu.recruit.common.response.Response;
import com.xiaohuashifu.recruit.pay.api.domain.CancelAction;
import lombok.Builder;
import lombok.Data;

/**
 * 描述：订单撤销响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
public class TradeCancelResponse implements Response {

    /**
     * 是否需要重试
     */
    private final Boolean needRetry;

    /**
     * 撤销订单时的行为
     */
    private final CancelAction action;

}
