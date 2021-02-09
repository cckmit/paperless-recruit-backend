package com.xiaohuashifu.recruit.pay.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：订单日志数据映射层
 *
 * @author xhsf
 * @create 2021/1/8 00:02
 */
public interface TradeLogMapper extends BaseMapper<TradeLogDO> {

    default TradeLogDO selectByOrderNumber(String orderNumber) {
        LambdaQueryWrapper<TradeLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeLogDO::getOrderNumber, orderNumber);
        return selectOne(wrapper);
    }

    int updateTradeStatus(@Param("id") Long id, @Param("oldTradeStatus") String oldTradeStatus,
                          @Param("newTradeStatus") String newTradeStatus);

    default List<TradeLogDO> selectListByTradeStatus(String tradeStatus) {
        LambdaQueryWrapper<TradeLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TradeLogDO::getTradeStatus, tradeStatus);
        return selectList(wrapper);
    }

}
