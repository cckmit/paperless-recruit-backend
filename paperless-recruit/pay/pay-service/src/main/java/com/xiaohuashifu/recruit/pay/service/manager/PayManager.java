package com.xiaohuashifu.recruit.pay.service.manager;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.service.dto.*;

/**
 * 描述：支付管理器
 *
 * @author xhsf
 * @create 2021/1/6 19:48
 */
public interface PayManager {

    /**
     * 预下单
     *
     * @errorCode UnknownError: 预下单失败
     *
     * @param preCreateDTO PreCreateDTO
     * @return 支付的二维码
     */
    Result<String> preCreate(PreCreateDTO preCreateDTO);

    /**
     * 查询订单
     *
     * @errorCode UnknownError: 查询失败
     *
     * @param orderNumber 订单号
     * @param tradeNumber 交易号，支付平台产生
     * @return 查询结果
     */
    Result<QueryResultDTO> query(String orderNumber, String tradeNumber);

    /**
     * 撤销订单
     *
     * @errorCode UnknownError: 撤销出错
     *
     * @param orderNumber 订单号
     * @param tradeNumber 交易号，支付平台产生
     * @return 撤销结果
     */
    Result<CancelResultDTO> cancel(String orderNumber, String tradeNumber);

    /**
     * 退款
     *
     * @errorCode UnknownError: 退款出错
     *
     * @param refundDTO 退款的参数对象
     * @return 退款结果
     */
    Result<RefundResultDTO> refund(RefundDTO refundDTO);
}
