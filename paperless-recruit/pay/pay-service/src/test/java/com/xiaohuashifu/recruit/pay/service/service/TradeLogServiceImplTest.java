package com.xiaohuashifu.recruit.pay.service.service;

import com.xiaohuashifu.recruit.pay.api.constant.TradeStatusEnum;
import com.xiaohuashifu.recruit.pay.api.service.PayService;
import com.xiaohuashifu.recruit.pay.api.service.TradeLogService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/8 20:39
 */
public class TradeLogServiceImplTest {

    private TradeLogService tradeLogService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("tradeLogServiceTest");
        ReferenceConfig<TradeLogService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20888/com.xiaohuashifu.recruit.pay.api.service.TradeLogService");
        reference.setApplication(application);
        reference.setInterface(TradeLogService.class);
        reference.setTimeout(10000000);
        tradeLogService = reference.get();
    }


    @Test
    public void getTradeLog() {
        System.out.println(tradeLogService.getTradeLog(1L));
    }

    @Test
    public void updateTradeStatus() {
        System.out.println(tradeLogService.updateTradeStatus(1L, TradeStatusEnum.WAIT_BUYER_PAY, TradeStatusEnum.TRADE_FINISHED));
    }
}