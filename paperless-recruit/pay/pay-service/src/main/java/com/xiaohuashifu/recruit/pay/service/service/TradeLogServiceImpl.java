package com.xiaohuashifu.recruit.pay.service.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
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

    private final TradeLogAssembler tradeLogAssembler;

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

    public TradeLogServiceImpl(TradeLogAssembler tradeLogAssembler, TradeLogMapper tradeLogMapper) {
        this.tradeLogAssembler = tradeLogAssembler;
        this.tradeLogMapper = tradeLogMapper;
    }

    @Override
    public TradeLogDTO getTradeLog(Long id) {
        TradeLogDO tradeLogDO = tradeLogMapper.selectById(id);
        if (tradeLogDO == null) {
            throw new NotFoundServiceException("tradeLog", "id", id);
        }
        return tradeLogAssembler.tradeLogDOToTradeLogDTO(tradeLogDO);
    }

    @Override
    public TradeLogDTO updateTradeStatus(Long id, TradeStatusEnum oldTradeStatus, TradeStatusEnum newTradeStatus) {
        // 判断是否存在
        getTradeLog(id);

        // 更新状态
        int count = tradeLogMapper.updateTradeStatus(id, oldTradeStatus.name(), newTradeStatus.name());
        if (count < 1) {
            throw new MisMatchServiceException("The oldTradeStatus does not is" + oldTradeStatus + ".");
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
        List<TradeLogDO> tradeLogDOList = tradeLogMapper.selectListByTradeStatus(
                TradeStatusEnum.WAIT_BUYER_SCAN.name());
        for (TradeLogDO tradeLogDO : tradeLogDOList) {
            PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(tradeLogDO.getPaymentMethod());
            TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder()
                    .orderNumber(tradeLogDO.getOrderNumber())
                    .paymentMethod(paymentMethod)
                    .build();
            TradeQueryResponse tradeQueryResponse;
            try {
                tradeQueryResponse = payService.query(tradeQueryRequest);
            }
            // 查询失败
            catch (ServiceException e) {
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
                    TradeStatusEnum.valueOf(tradeQueryResponse.getTradeStatus()));
        }
    }

    /**
     * 更新交易状态 WAIT_BUYER_SCAN
     */
    private void updateTradeStatusWhenWaitBuyerPay() {
        List<TradeLogDO> tradeLogDOList = tradeLogMapper.selectListByTradeStatus(
                TradeStatusEnum.WAIT_BUYER_PAY.name());
        for (TradeLogDO tradeLogDO : tradeLogDOList) {
            PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(tradeLogDO.getPaymentMethod());
            TradeQueryRequest tradeQueryRequest = TradeQueryRequest.builder()
                    .orderNumber(tradeLogDO.getOrderNumber())
                    .paymentMethod(paymentMethod)
                    .build();
            try {
                TradeQueryResponse tradeQueryResponse = payService.query(tradeQueryRequest);
                TradeStatusEnum tradeStatus = TradeStatusEnum.valueOf(tradeQueryResponse.getTradeStatus());
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
                        TradeStatusEnum.valueOf(tradeQueryResponse.getTradeStatus()));
            } catch (ServiceException ignored) {
            }
        }
    }

}
