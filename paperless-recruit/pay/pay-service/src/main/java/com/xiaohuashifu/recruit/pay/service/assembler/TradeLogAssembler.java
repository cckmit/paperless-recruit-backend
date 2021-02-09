package com.xiaohuashifu.recruit.pay.service.assembler;

import com.xiaohuashifu.recruit.pay.api.dto.TradeLogDTO;
import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.mapstruct.Mapper;

/**
 * 描述：TradeLog 装配器
 *
 * @author xhsf
 * @create 2021/1/8 20:26
 */
@Mapper(componentModel = "spring")
public interface TradeLogAssembler {

    TradeLogDTO tradeLogDOToTradeLogDTO(TradeLogDO tradeLogDO);

}
