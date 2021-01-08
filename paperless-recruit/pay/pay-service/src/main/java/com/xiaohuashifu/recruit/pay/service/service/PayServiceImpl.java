package com.xiaohuashifu.recruit.pay.service.service;


import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
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
import com.xiaohuashifu.recruit.pay.service.dto.PreCreateDTO;
import com.xiaohuashifu.recruit.pay.service.manager.PayManager;
import com.xiaohuashifu.recruit.pay.service.manager.impl.AlipayManagerImpl;
import lombok.NonNull;
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
     * 预下单的锁定键模式，{0}是订单号
     */
    private static final String PRE_CREATE_LOCK_KEY_PATTERN = "pay:pre-create:order-number:{0}";

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
    @DistributedLock(value = PRE_CREATE_LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}",
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
                    .tradeStatus(TradeStatusEnum.WAIT_BUYER_PAY.name())
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
