package com.xiaohuashifu.recruit.pay.service.service;


import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.api.request.TradeCancelRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradePreCreateRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeQueryRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeRefundRequest;
import com.xiaohuashifu.recruit.pay.api.response.TradeCancelResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradePreCreateResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeQueryResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeRefundResponse;
import com.xiaohuashifu.recruit.pay.api.service.PayService;
import lombok.NonNull;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：支付服务
 *
 * @author xhsf
 * @create 2021/1/6 15:45
 */
@Service
public class PayServiceImpl implements PayService {

    /**
     * 预下单
     *
     * @private 内部方法
     *
     * @param request 预下单参数
     * @return 二维码
     */
    @Override
    public Result<TradePreCreateResponse> preCreate(TradePreCreateRequest request) {
//        request.getPaymentMethod()
        return null;
    }

    /**
     * 查询订单
     *
     * @param request 查询参数
     * @return 查询结果
     * @private 内部方法
     */
    @Override
    public Result<TradeQueryResponse> query(@NonNull TradeQueryRequest request) {
        return null;
    }

    /**
     * 撤销订单
     *
     * @param request 撤销订单请求
     * @return 撤销结果
     * @private 内部方法
     */
    @Override
    public Result<TradeCancelResponse> cancel(@NonNull TradeCancelRequest request) {
        return null;
    }

    /**
     * 退款
     *
     * @param request 退款请求
     * @return 退款结果
     * @private 内部方法
     */
    @Override
    public Result<TradeRefundResponse> refund(@NonNull TradeRefundRequest request) {
        return null;
    }
}
