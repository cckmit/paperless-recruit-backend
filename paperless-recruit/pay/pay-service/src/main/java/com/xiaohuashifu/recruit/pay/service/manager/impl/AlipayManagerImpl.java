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
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.service.dto.CancelResultDTO;
import com.xiaohuashifu.recruit.pay.service.dto.PreCreateDTO;
import com.xiaohuashifu.recruit.pay.service.dto.QueryResultDTO;
import com.xiaohuashifu.recruit.pay.service.dto.RefundResultDTO;
import com.xiaohuashifu.recruit.pay.service.manager.PayManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 描述：Alipay Manager
 *
 * @author xhsf
 * @create 2021/1/6 19:10
 */
@Component
public class AlipayManagerImpl implements PayManager {

    private final AlipayClient alipayClient;

    public AlipayManagerImpl(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }

    @Override
    public Result<String> preCreate(PreCreateDTO payPreCreateDTO) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel alipayTradePrecreateModel = new AlipayTradePrecreateModel();
        alipayTradePrecreateModel.setOutTradeNo(payPreCreateDTO.getOrderNumber());
        BigDecimal totalAmount = new BigDecimal(payPreCreateDTO.getTotalAmount()).divide(new BigDecimal("100"));
        alipayTradePrecreateModel.setTotalAmount(totalAmount.toString());
        alipayTradePrecreateModel.setSubject(payPreCreateDTO.getDescription());
        alipayTradePrecreateModel.setTimeoutExpress(payPreCreateDTO.getExpireTime() + "m");
        request.setBizModel(alipayTradePrecreateModel);
        AlipayTradePrecreateResponse response;
        try {
            response = alipayClient.certificateExecute(request);
            System.out.println(response.getBody());
        } catch (AlipayApiException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return Result.success(response.getQrCode());
    }

    @Override
    public Result<QueryResultDTO> query(String orderNumber) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
        alipayTradeQueryModel.setOutTradeNo(orderNumber);
        AlipayTradeQueryResponse response;
        request.setBizModel(alipayTradeQueryModel);
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        String code = response.getCode();
        if (!code.equals("10000")) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "The order does not exist.");
        }
        Integer totalAmount = new BigDecimal(response.getTotalAmount())
                .multiply(new BigDecimal("100")).intValue();
        Integer buyerPayAmount = new BigDecimal(response.getBuyerPayAmount())
                .multiply(new BigDecimal("100")).intValue();
        String orderStatus = response.getTradeStatus();
        QueryResultDTO queryResultDTO = QueryResultDTO.builder()
                .totalAmount(totalAmount)
                .buyerPayAmount(buyerPayAmount)
                .orderStatus(orderStatus)
                .build();
        return Result.success(queryResultDTO);
    }

    @Override
    public Result<CancelResultDTO> cancel(String orderNumber) {
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel alipayTradeCancelModel = new AlipayTradeCancelModel();
        alipayTradeCancelModel.setOutTradeNo(orderNumber);
        AlipayTradeCancelResponse response;
        request.setBizModel(alipayTradeCancelModel);
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        CancelResultDTO cancelResultDTO = CancelResultDTO.builder()
                .needRetry(response.getRetryFlag().equals("Y"))
                .action(response.getAction())
                .build();
        return Result.success(cancelResultDTO);
    }

    @Override
    public Result<RefundResultDTO> refund(String orderNumber, Integer refundAmount) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
        alipayTradeRefundModel.setOutTradeNo(orderNumber);
        String amount = new BigDecimal(refundAmount).divide(new BigDecimal(100)).toString();
        alipayTradeRefundModel.setRefundAmount(amount);
        AlipayTradeRefundResponse response;
        request.setBizModel(alipayTradeRefundModel);
        try {
            response = alipayClient.certificateExecute(request);
        } catch (AlipayApiException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        String code = response.getCode();
        if (!code.equals("10000")) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS, "The order already cancel.");
        }
        RefundResultDTO refundResultDTO = RefundResultDTO.builder()
                .fundChange(response.getFundChange().equals("Y"))
                .refundFee(new BigDecimal(response.getRefundFee()).multiply(new BigDecimal("100")).intValue())
                .build();
        return Result.success(refundResultDTO);
    }

}
