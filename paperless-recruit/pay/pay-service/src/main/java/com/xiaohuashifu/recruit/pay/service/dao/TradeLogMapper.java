package com.xiaohuashifu.recruit.pay.service.dao;

import com.xiaohuashifu.recruit.pay.service.do0.TradeLogDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：订单日志数据映射层
 *
 * @author xhsf
 * @create 2021/1/8 00:02
 */
public interface TradeLogMapper {
    int insertTradeLog(TradeLogDO tradeLogDO);

    int countByOrderNumber(String orderNumber);

    int updateQrCode(@Param("id") Long id, @Param("qrCode") String qrCode);
}
