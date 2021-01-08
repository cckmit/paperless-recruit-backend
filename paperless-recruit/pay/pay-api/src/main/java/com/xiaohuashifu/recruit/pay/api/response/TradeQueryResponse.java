package com.xiaohuashifu.recruit.pay.api.response;

import com.xiaohuashifu.recruit.common.response.Response;
import com.xiaohuashifu.recruit.pay.api.domain.Money;
import com.xiaohuashifu.recruit.pay.api.domain.TradeNumber;
import com.xiaohuashifu.recruit.pay.api.constant.TradeStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 描述：订单查询响应
 *
 * @author xhsf
 * @create 2021/1/7 17:14
 */
@Data
@Builder
public class TradeQueryResponse implements Response {

    /**
     * 平台订单号
     */
    private final TradeNumber tradeNumber;

    /**
     * 买家付款金额
     */
    private final Money buyerPayAmount;

    /**
     * 总金额
     */
    private final Money totalAmount;

    /**
     * 订单状态
     */
    private final TradeStatusEnum tradeStatus;

}
