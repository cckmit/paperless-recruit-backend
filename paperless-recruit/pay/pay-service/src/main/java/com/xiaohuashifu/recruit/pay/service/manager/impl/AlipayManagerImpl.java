package com.xiaohuashifu.recruit.pay.service.manager.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
import com.xiaohuashifu.recruit.common.util.RmbUtils;
import com.xiaohuashifu.recruit.pay.service.dto.*;
import com.xiaohuashifu.recruit.pay.service.manager.PayManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：Alipay Manager
 *
 * @author xhsf
 * @create 2021/1/6 19:10
 */
@Slf4j
@Component
public class AlipayManagerImpl implements PayManager {

    private final AlipayClient alipayClient;

    /**
     * alipay 请求成功的 code
     */
    private static final String ALIPAY_REQUEST_SUCCESS_CODE = "10000";

    /**
     * alipay 的真的标志
     */
    private static final String ALIPAY_TRUE_FLAG = "Y";

    public AlipayManagerImpl(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    @Override
    public String preCreate(PreCreateDTO preCreateDTO) {
        // 封装请求
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel alipayTradePrecreateModel = new AlipayTradePrecreateModel();
        alipayTradePrecreateModel.setOutTradeNo(preCreateDTO.getOrderNumber());
        alipayTradePrecreateModel.setTotalAmount(RmbUtils.fen2Yuan(preCreateDTO.getTotalAmount()));
        alipayTradePrecreateModel.setSubject(preCreateDTO.getSubject());
        alipayTradePrecreateModel.setTimeoutExpress(preCreateDTO.getExpireTime() + "m");
        request.setBizModel(alipayTradePrecreateModel);

        // 发送请求
        AlipayTradePrecreateResponse response;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            log.error("Alipay preCreate request error. preCreateDTO:" + preCreateDTO, e);
            throw new InternalServiceException("PreCreate trade error.");
        }

        // 如果状态码不是 10000，表示请求失败
        if (!Objects.equals(response.getCode(), ALIPAY_REQUEST_SUCCESS_CODE)) {
            log.warn("Alipay preCreate request failed. preCreateDTO={}, response={}", preCreateDTO, response);
            throw new InternalServiceException("PreCreate trade failed.");
        }

        // 请求成功返回二维码
        return response.getQrCode();
    }

    @Override
    public QueryResultDTO query(String orderNumber, String tradeNumber) {
        // 封装请求参数
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
        if (orderNumber != null) {
            alipayTradeQueryModel.setOutTradeNo(orderNumber);
        } else {
            alipayTradeQueryModel.setTradeNo(tradeNumber);
        }
        request.setBizModel(alipayTradeQueryModel);

        // 发送请求
        AlipayTradeQueryResponse response;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            log.error("Alipay query request error. orderNumber: " + orderNumber
                    + ", tradeNumber: " + tradeNumber + ".", e);
            throw new InternalServiceException("Query trade error.");
        }

        // 如果状态码不是 10000，表示请求失败
        if (!Objects.equals(response.getCode(), ALIPAY_REQUEST_SUCCESS_CODE)) {
            log.warn("Alipay query request failed. orderNumber: " + orderNumber
                    + ", tradeNumber: " + tradeNumber + ".");
            throw new InternalServiceException("Query trade failed.");
        }

        // 封装查询结果
        return QueryResultDTO.builder()
                .totalAmount(RmbUtils.yuan2Fen(response.getTotalAmount()))
                .buyerPayAmount(RmbUtils.yuan2Fen(response.getBuyerPayAmount()))
                .tradeStatus(response.getTradeStatus())
                .tradeNumber(response.getTradeNo())
                .build();
    }

    @Override
    public CancelResultDTO cancel(String orderNumber, String tradeNumber) {
        // 封装请求
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel alipayTradeCancelModel = new AlipayTradeCancelModel();
        if (orderNumber != null) {
            alipayTradeCancelModel.setOutTradeNo(orderNumber);
        } else {
            alipayTradeCancelModel.setTradeNo(tradeNumber);
        }
        request.setBizModel(alipayTradeCancelModel);

        // 发送请求
        AlipayTradeCancelResponse response;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            log.error("Alipay cancel request error. orderNumber: " + orderNumber
                    + ", tradeNumber: " + tradeNumber + ".", e);
            throw new InternalServiceException("Cancel trade error.");
        }

        // 如果状态码不是 10000，表示请求失败
        if (!Objects.equals(response.getCode(), ALIPAY_REQUEST_SUCCESS_CODE)) {
            log.warn("Alipay cancel request failed. orderNumber: " + orderNumber
                    + ", tradeNumber: " + tradeNumber + ".");
            throw new InternalServiceException("Cancel trade failed.");
        }

        // 封装结果
        return CancelResultDTO.builder()
                .needRetry(Objects.equals(response.getRetryFlag(), ALIPAY_TRUE_FLAG))
                .action(response.getAction())
                .build();
    }

    @Override
    public RefundResultDTO refund(RefundDTO refundDTO) {
        // 封装请求
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
        if (refundDTO.getOrderNumber() != null) {
            alipayTradeRefundModel.setOutTradeNo(refundDTO.getOrderNumber());
        } else {
            alipayTradeRefundModel.setTradeNo(refundDTO.getTradeNumber());
        }
        alipayTradeRefundModel.setRefundAmount(RmbUtils.fen2Yuan(refundDTO.getRefundAmount()));
        if (refundDTO.getRefundNumber() != null) {
            alipayTradeRefundModel.setOutRequestNo(refundDTO.getRefundNumber());
        }
        request.setBizModel(alipayTradeRefundModel);

        // 发送请求
        AlipayTradeRefundResponse response;
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            log.error("Alipay refund request error. RefundDTO: " + refundDTO, e);
            throw new InternalServiceException("Refund trade error.");
        }

        // 如果状态码不是 10000，表示请求失败
        if (!Objects.equals(response.getCode(), ALIPAY_REQUEST_SUCCESS_CODE)) {
            log.error("Alipay refund request failed. RefundDTO: " + refundDTO);
            throw new InternalServiceException("Refund trade failed.");
        }

        // 封装请求结果
        return RefundResultDTO.builder()
                .fundChange(Objects.equals(response.getFundChange(), ALIPAY_TRUE_FLAG))
                .refundFee(RmbUtils.yuan2Fen(response.getRefundFee()))
                .build();
    }

}
