package com.xiaohuashifu.recruit.pay.service.assembler;

import com.xiaohuashifu.recruit.pay.api.dto.TradeLogDTO;
import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：TradeLog 装配器
 *
 * @author xhsf
 * @create 2021/1/8 20:26
 */
@Mapper
public interface TradeLogAssembler {

    TradeLogAssembler INSTANCE = Mappers.getMapper(TradeLogAssembler.class);

    TradeLogDTO toDTO(TradeLogDO tradeLogDO);

}
