package com.xiaohuashifu.recruit.pay.service.manager;

import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
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
     * @param preCreateDTO PreCreateDTO
     * @return 支付的二维码
     */
    String preCreate(PreCreateDTO preCreateDTO) throws InternalServiceException;

    /**
     * 查询订单
     *
     * @param orderNumber 订单号
     * @param tradeNumber 交易号，支付平台产生
     * @return 查询结果
     */
    QueryResultDTO query(String orderNumber, String tradeNumber) throws InternalServiceException;

    /**
     * 撤销订单
     *
     * @param orderNumber 订单号
     * @param tradeNumber 交易号，支付平台产生
     * @return 撤销结果
     */
    CancelResultDTO cancel(String orderNumber, String tradeNumber) throws InternalServiceException;

    /**
     * 退款
     *
     * @param refundDTO 退款的参数对象
     * @return 退款结果
     */
    RefundResultDTO refund(RefundDTO refundDTO) throws InternalServiceException;

}
