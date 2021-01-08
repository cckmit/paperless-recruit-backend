package com.xiaohuashifu.recruit.pay.service.dao;

import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：订单日志数据映射层
 *
 * @author xhsf
 * @create 2021/1/8 00:02
 */
public interface TradeLogMapper {
    int insertTradeLog(TradeLogDO tradeLogDO);

    TradeLogDO getTradeLog(Long id);

    TradeLogDO getTradeLogByOrderNumber(String orderNumber);

    List<TradeLogDO> listTradeLogsByTradeStatus(String tradeStatus);

    int count(Long id);

    int countByOrderNumber(String orderNumber);

    int updateQrCode(@Param("id") Long id, @Param("qrCode") String qrCode);

    int updateTradeStatus(@Param("id") Long id, @Param("tradeStatus") String tradeStatus);

    int updateTradeStatus0(@Param("id") Long id, @Param("oldTradeStatus") String oldTradeStatus,
                           @Param("newTradeStatus") String newTradeStatus);

    int updateCancelAction(@Param("id") Long id, @Param("cancelAction") String cancelAction);

}
