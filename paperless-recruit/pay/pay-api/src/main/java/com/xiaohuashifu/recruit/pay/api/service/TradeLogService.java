package com.xiaohuashifu.recruit.pay.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 找不到该日志
     *
     * @param id 交易日志编号
     * @return TradeLogDTO
     */
    Result<TradeLogDTO> getTradeLog(@NotNull(message = "The id can't be null.")
                                    @Positive(message = "The id must be greater than 0.") Long id);

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
    Result<TradeLogDTO> updateTradeStatus(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The oldTradeStatus can't be null.") TradeStatusEnum oldTradeStatus,
            @NotNull(message = "The newTradeStatus can't be null.") TradeStatusEnum newTradeStatus);

}
