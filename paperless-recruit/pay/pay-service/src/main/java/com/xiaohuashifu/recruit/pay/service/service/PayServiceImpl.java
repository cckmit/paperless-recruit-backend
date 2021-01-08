package com.xiaohuashifu.recruit.pay.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.api.constant.CancelActionEnum;
import com.xiaohuashifu.recruit.pay.api.constant.TradeStatusEnum;
import com.xiaohuashifu.recruit.pay.api.request.TradeCancelRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradePreCreateRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeQueryRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeRefundRequest;
import com.xiaohuashifu.recruit.pay.api.response.TradeCancelResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradePreCreateResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeQueryResponse;
import com.xiaohuashifu.recruit.pay.api.response.TradeRefundResponse;
import com.xiaohuashifu.recruit.pay.api.service.PayService;
import com.xiaohuashifu.recruit.pay.service.dao.TradeLogMapper;
import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import com.xiaohuashifu.recruit.pay.service.dto.*;
import com.xiaohuashifu.recruit.pay.service.manager.PayManager;
import com.xiaohuashifu.recruit.pay.service.manager.impl.AlipayManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * 描述：支付服务
 *
 * @author xhsf
 * @create 2021/1/6 15:45
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private final TradeLogMapper tradeLogMapper;

    private final PlatformTransactionManager transactionManager;

    private final TransactionDefinition transactionDefinition;

    private final PayManager alipayManager;

    /**
     * 订单的锁定键模式，{0}是订单号
     */
    private static final String LOCK_KEY_PATTERN = "pay:order-number:{0}";

    public PayServiceImpl(TradeLogMapper tradeLogMapper, PlatformTransactionManager transactionManager,
                          TransactionDefinition transactionDefinition, AlipayManagerImpl alipayManager) {
        this.tradeLogMapper = tradeLogMapper;
        this.transactionManager = transactionManager;
        this.transactionDefinition = transactionDefinition;
        this.alipayManager = alipayManager;
    }

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
    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}",
            errorMessage = "Failed to acquire orderNumber lock.")
    @Override
    public Result<TradePreCreateResponse> preCreate(TradePreCreateRequest request) {
        // 判断订单号存不存在
        int count = tradeLogMapper.countByOrderNumber(request.getOrderNumber());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The orderNumber already exist.");
        }

        // 在数据库插入订单
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            TradeLogDO tradeLogDO = TradeLogDO.builder()
                    .paymentMethod(request.getPaymentMethod().name())
                    .orderNumber(request.getOrderNumber())
                    .totalAmount(request.getTotalAmount())
                    .tradeSubject(request.getSubject())
                    .expireTime(request.getExpireTime())
                    .tradeStatus(TradeStatusEnum.WAIT_BUYER_SCAN.name())
                    .build();
            tradeLogMapper.insertTradeLog(tradeLogDO);

            // 预创建订单
            Result<String> preCreateResult = alipayManager.preCreate(PreCreateDTO.builder()
                    .subject(request.getSubject())
                    .expireTime(request.getExpireTime())
                    .orderNumber(request.getOrderNumber())
                    .totalAmount(request.getTotalAmount())
                    .build());
            if (preCreateResult.isFailure()) {
                transactionManager.rollback(transactionStatus);
                return Result.fail(preCreateResult);
            }
            transactionManager.commit(transactionStatus);

            // 插入二维码到数据库
            String qrCode = preCreateResult.getData();
            tradeLogMapper.updateQrCode(tradeLogDO.getId(), qrCode);

            // 把二维码和订单日志编号返回
            TradePreCreateResponse tradePreCreateResponse = TradePreCreateResponse.builder()
                    .qrCode(qrCode)
                    .tradeLogId(tradeLogDO.getId())
                    .build();
            return Result.success(tradePreCreateResponse);
        } catch (RuntimeException e) {
            log.error("Pre create trade false. request=" + request, e);
            transactionManager.rollback(transactionStatus);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    /**
     * 查询订单
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              UnknownError: 查询失败
     *
     * @param request 查询参数
     * @return 查询结果
     */
    @Override
    public Result<TradeQueryResponse> query(TradeQueryRequest request) {
        // 查询
        Result<QueryResultDTO> queryResult = alipayManager.query(request.getOrderNumber(), null);
        if (queryResult.isFailure()) {
            return Result.fail(queryResult);
        }

        // 封装查询结果
        QueryResultDTO queryResultDTO = queryResult.getData();
        TradeQueryResponse tradeQueryResponse = TradeQueryResponse.builder()
                .tradeNumber(queryResultDTO.getTradeNumber())
                .buyerPayAmount(queryResultDTO.getBuyerPayAmount())
                .totalAmount(queryResultDTO.getTotalAmount())
                .tradeStatus(queryResultDTO.getTradeStatus())
                .build();
        return Result.success(tradeQueryResponse);
    }

    /**
     * 撤销订单
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 订单不存在
     *              OperationConflict.Status: 订单已经关闭
     *              OperationConflict.Lock: 获取订单号的锁失败
     *              UnknownError: 撤销失败
     *
     * @param request 撤销订单请求
     * @return 撤销结果
     */
    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}",
            errorMessage = "Failed to acquire orderNumber lock.")
    @Override
    public Result<TradeCancelResponse> cancel(TradeCancelRequest request) {
        // 判断订单号存不存在
        TradeLogDO tradeLogDO = tradeLogMapper.getTradeLogByOrderNumber(request.getOrderNumber());
        if (tradeLogDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The trade does not exist.");
        }

        // 判断订单是否已经关闭
        if (TradeStatusEnum.TRADE_CLOSED.name().equals(tradeLogDO.getTradeStatus())) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS, "The trade already cancel.");
        }

        // 撤销订单
        Result<CancelResultDTO> cancelResult = alipayManager.cancel(request.getOrderNumber(), null);
        if (cancelResult.isFailure()) {
            return Result.fail(cancelResult);
        }

        // 更新订单日志
        tradeLogMapper.updateTradeStatus(tradeLogDO.getId(), TradeStatusEnum.TRADE_CLOSED.name());

        // 更新撤销时的动作
        CancelResultDTO cancelResultDTO = cancelResult.getData();
        String action = CancelActionEnum.getActionName(cancelResultDTO.getAction());
        tradeLogMapper.updateCancelAction(tradeLogDO.getId(), action);

        // 封装撤销订单结果
        TradeCancelResponse tradeCancelResponse = TradeCancelResponse.builder()
                .action(action)
                .build();
        return Result.success(tradeCancelResponse);
    }

    /**
     * 退款
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 订单不存在
     *              OperationConflict.Status: 订单状态不是 TRADE_SUCCESS
     *              OperationConflict.Lock: 获取订单号的锁失败
     *              UnknownError: 退款失败
     *
     * @param request 退款请求
     * @return 退款结果
     */
    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}",
            errorMessage = "Failed to acquire orderNumber lock.")
    @Override
    public Result<TradeRefundResponse> refund(TradeRefundRequest request) {
        // 判断订单号存不存在
        TradeLogDO tradeLogDO = tradeLogMapper.getTradeLogByOrderNumber(request.getOrderNumber());
        if (tradeLogDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The trade does not exist.");
        }

        // 判断订单是不是 TRADE_SUCCESS
        if (!TradeStatusEnum.TRADE_SUCCESS.name().equals(tradeLogDO.getTradeStatus())) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The status of trade must be TRADE_SUCCESS.");
        }

        // 撤销订单
        RefundDTO refundDTO = RefundDTO.builder()
                .orderNumber(request.getOrderNumber())
                .refundAmount(request.getRefundAmount()).build();
        Result<RefundResultDTO> refundResult = alipayManager.refund(refundDTO);
        if (refundResult.isFailure()) {
            return Result.fail(refundResult);
        }

        // 更新订单日志
        tradeLogMapper.updateTradeStatus(tradeLogDO.getId(), TradeStatusEnum.TRADE_CLOSED.name());

        // 封装退款结果
        RefundResultDTO refundResultDTO = refundResult.getData();
        TradeRefundResponse tradeRefundResponse = TradeRefundResponse.builder()
                .fundChange(refundResultDTO.getFundChange())
                .refundFee(refundResultDTO.getRefundFee())
                .build();
        return Result.success(tradeRefundResponse);
    }

}
