package com.xiaohuashifu.recruit.pay.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import com.xiaohuashifu.recruit.pay.api.constant.TradeStatusEnum;
import com.xiaohuashifu.recruit.pay.api.dto.TradeLogDTO;
import com.xiaohuashifu.recruit.pay.api.request.TradeCancelRequest;
import com.xiaohuashifu.recruit.pay.api.request.TradeQueryRequest;
import com.xiaohuashifu.recruit.pay.api.response.TradeQueryResponse;
import com.xiaohuashifu.recruit.pay.api.service.PayService;
import com.xiaohuashifu.recruit.pay.api.service.TradeLogService;
import com.xiaohuashifu.recruit.pay.service.assembler.TradeLogAssembler;
import com.xiaohuashifu.recruit.pay.service.dao.TradeLogMapper;
import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：交易日志服务
 *
 * @author xhsf
 * @create 2021/1/8 20:13
 */
@Service
public class TradeLogServiceImpl implements TradeLogService {

    private final TradeLogAssembler tradeLogAssembler = TradeLogAssembler.INSTANCE;

    private final TradeLogMapper tradeLogMapper;

    @Reference
    private PayService payService;

    /**
     * 更新订单状态初始延迟
     */
    private static final int UPDATE_TRADE_STATUS_INITIAL_DELAY = 10000;

    /**
     * 更新订单状态固定延迟
     */
    private static final int UPDATE_TRADE_STATUS_FIXED_DELAY = 30000;

    public TradeLogServiceImpl(TradeLogMapper tradeLogMapper) {
        this.tradeLogMapper = tradeLogMapper;
    }

    /**
     * 获取交易日志
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 找不到该日志
     *
     * @param id 交易日志编号
     * @return TradeLogDTO
     */
    @Override
    public Result<TradeLogDTO> getTradeLog(Long id) {
        TradeLogDO tradeLogDO = tradeLogMapper.getTradeLog(id);
        if (tradeLogDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "The tradeLog does not exist.");
        }
        return Result.success(tradeLogAssembler.toDTO(tradeLogDO));
    }

    /**
     * 更新交易状态
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 日志不存在
     *              OperationConflict.Unmodified: 旧交易状态已经改变
     *
     * @param id 交易日志编号
     * @param oldTradeStatus 旧交易状态
     * @param newTradeStatus 新交易状态
     * @return 更新后的交易日志
     */
    @Override
    public Result<TradeLogDTO> updateTradeStatus(Long id, TradeStatusEnum oldTradeStatus,
                                                 TradeStatusEnum newTradeStatus) {
        // 判断是否存在
        int count = tradeLogMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The tradeLog does not exist.");
        }

        // 更新状态
        count = tradeLogMapper.updateTradeStatus0(id, oldTradeStatus.name(), newTradeStatus.name());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The oldTradeStatus does not is" + oldTradeStatus + ".");
        }

        // 获取更新后的交易日志
        return getTradeLog(id);
    }

    /**
     * 定时更新订单状态
     */
    @Scheduled(initialDelay = UPDATE_TRADE_STATUS_INITIAL_DELAY, fixedDelay = UPDATE_TRADE_STATUS_FIXED_DELAY)
    public void updateTradeStatus() {
        // 更新交易状态 WAIT_BUYER_SCAN
        updateTradeStatusWhenWaitBuyerScan();

        // 更新交易状态 WAIT_BUYER_SCAN
        updateTradeStatusWhenWaitBuyerPay();
    }

    /**
     * 更新交易状态 WAIT_BUYER_SCAN
     */
    private void updateTradeStatusWhenWaitBuyerScan() {
        List<TradeLogDO> tradeLogDOList = tradeLogMapper.listTradeLogsByTradeStatus(
                TradeStatusEnum.WAIT_BUYER_SCAN.name());
        for (TradeLogDO tradeLogDO : tradeLogDOList) {
            PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(tradeLogDO.getPaymentMethod());
            TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder()
                    .orderNumber(tradeLogDO.getOrderNumber())
                    .paymentMethod(paymentMethod)
                    .build();
            Result<TradeQueryResponse> queryResult = payService.query(tradeQueryRequest);
            // 查询失败
            if (queryResult.isFailure()) {
                // 订单已经过期，则关闭订单
                if (tradeLogDO.getCreateTime().plusMinutes(tradeLogDO.getExpireTime()).isBefore(LocalDateTime.now())) {
                    TradeCancelRequest tradeCancelRequest = TradeCancelRequest.builder()
                            .paymentMethod(paymentMethod)
                            .orderNumber(tradeLogDO.getOrderNumber())
                            .build();
                    payService.cancel(tradeCancelRequest);
                }
                continue;
            }

            // 否则直接更新订单状态
            updateTradeStatus(tradeLogDO.getId(), TradeStatusEnum.WAIT_BUYER_SCAN,
                    TradeStatusEnum.valueOf(queryResult.getData().getTradeStatus()));
        }
    }

    /**
     * 更新交易状态 WAIT_BUYER_SCAN
     */
    private void updateTradeStatusWhenWaitBuyerPay() {
        List<TradeLogDO> tradeLogDOList = tradeLogMapper.listTradeLogsByTradeStatus(
                TradeStatusEnum.WAIT_BUYER_PAY.name());
        for (TradeLogDO tradeLogDO : tradeLogDOList) {
            PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(tradeLogDO.getPaymentMethod());
            TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder()
                    .orderNumber(tradeLogDO.getOrderNumber())
                    .paymentMethod(paymentMethod)
                    .build();
            Result<TradeQueryResponse> queryResult = payService.query(tradeQueryRequest);

            // 查询成功
            if (queryResult.isSuccess()) {
                TradeQueryResponse queryResponse = queryResult.getData();
                TradeStatusEnum tradeStatus = TradeStatusEnum.valueOf(queryResponse.getTradeStatus());
                // 如果订单状态还是 WAIT_BUYER_PAY
                if (tradeStatus == TradeStatusEnum.WAIT_BUYER_PAY) {
                    // 订单已经过期，则关闭订单
                    if (tradeLogDO.getCreateTime().plusMinutes(tradeLogDO.getExpireTime()).isBefore(LocalDateTime.now())) {
                        TradeCancelRequest tradeCancelRequest = TradeCancelRequest.builder()
                                .paymentMethod(paymentMethod)
                                .orderNumber(tradeLogDO.getOrderNumber())
                                .build();
                        payService.cancel(tradeCancelRequest);
                    }
                    continue;
                }

                // 否则直接更新订单状态
                updateTradeStatus(tradeLogDO.getId(), TradeStatusEnum.WAIT_BUYER_PAY,
                        TradeStatusEnum.valueOf(queryResult.getData().getTradeStatus()));
            }
        }
    }
}
