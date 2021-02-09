package com.xiaohuashifu.recruit.pay.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.pay.api.constant.TradeStatusEnum;
import com.xiaohuashifu.recruit.pay.api.dto.TradeLogDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：交易日志服务
 *
 * @author xhsf
 * @create 2021/1/8 20:13
 */
public interface TradeLogService {

    /**
     * 获取交易日志
     *
     * @private 内部方法
     *
     * @param id 交易日志编号
     * @return TradeLogDTO
     */
    TradeLogDTO getTradeLog(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 更新交易状态
     *
     * @private 内部方法
     *
     * @param id 交易日志编号
     * @param oldTradeStatus 旧交易状态
     * @param newTradeStatus 新交易状态
     * @return 更新后的交易日志
     */
    TradeLogDTO updateTradeStatus(
            @NotNull @Positive Long id, @NotNull TradeStatusEnum oldTradeStatus,
            @NotNull TradeStatusEnum newTradeStatus);

}
