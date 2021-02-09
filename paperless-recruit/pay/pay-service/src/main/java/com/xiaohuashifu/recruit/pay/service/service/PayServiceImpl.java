package com.xiaohuashifu.recruit.pay.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
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
import org.springframework.transaction.annotation.Transactional;

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

    private final PayManager alipayManager;

    /**
     * 订单的锁定键模式，{0}是订单号
     */
    private static final String LOCK_KEY_PATTERN = "pay:order-number:{0}";

    public PayServiceImpl(TradeLogMapper tradeLogMapper, AlipayManagerImpl alipayManager) {
        this.tradeLogMapper = tradeLogMapper;
        this.alipayManager = alipayManager;
    }

    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}")
    @Override
    @Transactional
    public TradePreCreateResponse preCreate(TradePreCreateRequest request) {
        // 判断订单号存不存在
        LambdaQueryWrapper<TradeLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeLogDO::getOrderNumber, request.getOrderNumber());
        int count = tradeLogMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The orderNumber already exist.");
        }

        // 在数据库插入订单
        TradeLogDO tradeLogDO = TradeLogDO.builder().paymentMethod(request.getPaymentMethod().name())
                .orderNumber(request.getOrderNumber()).totalAmount(request.getTotalAmount())
                .tradeSubject(request.getSubject()).expireTime(request.getExpireTime())
                .tradeStatus(TradeStatusEnum.WAIT_BUYER_SCAN.name()).build();
        tradeLogMapper.insert(tradeLogDO);

        // 预创建订单
        String qrCode = alipayManager.preCreate(PreCreateDTO.builder().subject(request.getSubject())
                .expireTime(request.getExpireTime()).orderNumber(request.getOrderNumber())
                .totalAmount(request.getTotalAmount()).build());

        // 插入二维码到数据库
        TradeLogDO tradeLogDOForUpdate = TradeLogDO.builder().id(tradeLogDO.getId()).qrCode(qrCode).build();
        tradeLogMapper.updateById(tradeLogDOForUpdate);

        // 把二维码和订单日志编号返回
        return TradePreCreateResponse.builder().qrCode(qrCode)
                .tradeLogId(tradeLogDO.getId()).build();
    }

    @Override
    public TradeQueryResponse query(TradeQueryRequest request) {
        // 查询
        QueryResultDTO queryResultDTO = alipayManager.query(request.getOrderNumber(), null);

        // 封装查询结果
        return TradeQueryResponse.builder().tradeNumber(queryResultDTO.getTradeNumber())
                .buyerPayAmount(queryResultDTO.getBuyerPayAmount()).totalAmount(queryResultDTO.getTotalAmount())
                .tradeStatus(queryResultDTO.getTradeStatus()).build();
    }

    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}")
    @Transactional
    @Override
    public TradeCancelResponse cancel(TradeCancelRequest request) {
        // 判断订单号存不存在
        TradeLogDO tradeLogDO = tradeLogMapper.selectByOrderNumber(request.getOrderNumber());
        if (tradeLogDO == null) {
            throw new NotFoundServiceException("The trade does not exist.");
        }

        // 判断订单是否已经关闭
        if (TradeStatusEnum.TRADE_CLOSED.name().equals(tradeLogDO.getTradeStatus())) {
            throw new InvalidStatusServiceException("The trade already cancel.");
        }

        // 撤销订单
        CancelResultDTO cancelResultDTO = alipayManager.cancel(request.getOrderNumber(), null);

        // 更新订单日志
        String action = CancelActionEnum.getActionName(cancelResultDTO.getAction());
        TradeLogDO tradeLogDOForUpdate = TradeLogDO.builder().id(tradeLogDO.getId())
                .tradeStatus(TradeStatusEnum.TRADE_CLOSED.name()).cancelAction(action).build();
        tradeLogMapper.updateById(tradeLogDOForUpdate);

        // 封装撤销订单结果
        return TradeCancelResponse.builder().action(action).build();
    }

    @Transactional
    @DistributedLock(value = LOCK_KEY_PATTERN, parameters = "#{#request.orderNumber}")
    @Override
    public TradeRefundResponse refund(TradeRefundRequest request) {
        // 判断订单号存不存在
        TradeLogDO tradeLogDO = tradeLogMapper.selectByOrderNumber(request.getOrderNumber());
        if (tradeLogDO == null) {
            throw new NotFoundServiceException("The trade does not exist.");
        }

        // 判断订单是不是 TRADE_SUCCESS
        if (!TradeStatusEnum.TRADE_SUCCESS.name().equals(tradeLogDO.getTradeStatus())) {
            throw new InvalidStatusServiceException("The status of trade must be TRADE_SUCCESS.");
        }

        // 撤销订单
        RefundDTO refundDTO = RefundDTO.builder().orderNumber(request.getOrderNumber())
                .refundAmount(request.getRefundAmount()).build();
        RefundResultDTO refundResultDTO = alipayManager.refund(refundDTO);

        // 更新订单日志
        TradeLogDO tradeLogDOForUpdate = TradeLogDO.builder().id(tradeLogDO.getId())
                .tradeStatus(TradeStatusEnum.TRADE_CLOSED.name()).build();
        tradeLogMapper.updateById(tradeLogDOForUpdate);

        // 封装退款结果
        return TradeRefundResponse.builder().fundChange(refundResultDTO.getFundChange())
                .refundFee(refundResultDTO.getRefundFee()).build();
    }

}
