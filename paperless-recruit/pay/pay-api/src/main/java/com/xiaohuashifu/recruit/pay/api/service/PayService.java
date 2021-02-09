package com.xiaohuashifu.recruit.pay.api.service;

import com.xiaohuashifu.recruit.common.exception.ServiceException;
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
     * @param request 预下单参数
     * @return 二维码
     */
    TradePreCreateResponse preCreate(@NotNull TradePreCreateRequest request) throws ServiceException;

    /**
     * 查询订单
     *
     * @private 内部方法
     *
     * @param request 查询参数
     * @return 查询结果
     */
    TradeQueryResponse query(@NotNull TradeQueryRequest request) throws ServiceException;

    /**
     * 撤销订单
     *
     * @private 内部方法
     *
     * @param request 撤销订单请求
     * @return 撤销结果
     */
    TradeCancelResponse cancel(@NotNull TradeCancelRequest request) throws ServiceException;

    /**
     * 退款
     *
     * @private 内部方法
     *
     * @param request 退款请求
     * @return 退款结果
     */
    TradeRefundResponse refund(@NotNull TradeRefundRequest request) throws ServiceException;

}
