package com.xiaohuashifu.recruit.pay.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.api.request.TradeCancelRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradePreCreateRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeQueryRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeRefundRequest;
import com.xiaohuashifu.recruit.pay.api.response.TradeCancelResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradePreCreateResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeQueryResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeRefundResponse;

import javax.validation.constraints.NotNull;

/**
 * 描述：支付服务
 *
 * @author xhsf
 * @create 2021/1/7 13:32
 */
public interface PayService {

    /**
     * 预下单
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Duplicate: 订单号已经存在
     *              OperationConflict.Lock: 获取订单号的锁失败
     *              InternalError: 内部错误
     *
     * @param request 预下单参数
     * @return 二维码
     */
    Result<TradePreCreateResponse> preCreate(@NotNull(message = "The request can't be null.")
                                                     TradePreCreateRequest request);

    /**
     * 查询订单
     *
     * @private 内部方法
     *
     * @param request 查询参数
     * @return 查询结果
     */
    Result<TradeQueryResponse> query(@NotNull TradeQueryRequest request);

    /**
     * 撤销订单
     *
     * @private 内部方法
     *
     * @param request 撤销订单请求
     * @return 撤销结果
     */
    Result<TradeCancelResponse> cancel(@NotNull TradeCancelRequest request);

    /**
     * 退款
     *
     * @private 内部方法
     *
     * @param request 退款请求
     * @return 退款结果
     */
    Result<TradeRefundResponse> refund(@NotNull TradeRefundRequest request);
}
