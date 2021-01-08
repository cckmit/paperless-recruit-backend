package com.xiaohuashifu.recruit.pay.service.service;

import com.xiaohuashifu.recruit.pay.api.constant.PaymentMethodEnum;
import com.xiaohuashifu.recruit.pay.api.domain.*;
import com.xiaohuashifu.recruit.pay.api.request.TradePreCreateRequest;
import com.xiaohuashifu.recruit.pay.api.service.PayService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/8 00:59
 */
public class PayServiceImplTest {

    private PayService payService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("payServiceTest");
        ReferenceConfig<PayService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20888/com.xiaohuashifu.recruit.pay.api.service.PayService");
        reference.setApplication(application);
        reference.setInterface(PayService.class);
        reference.setTimeout(10000000);
        payService = reference.get();
    }


    @Test
    public void preCreate() {
        BusinessNumber businessNumber = new BusinessNumber(1);
        CompactTime time = new CompactTime(LocalDateTime.now());
        OrderSerialNumber orderSerialNumber = new OrderSerialNumber("00001");
        OrderNumber orderNumber = new OrderNumber(businessNumber, time, orderSerialNumber);
        TradePreCreateRequest tradePreCreateRequest =
                TradePreCreateRequest.builder()
                        .paymentMethod(PaymentMethodEnum.ALIPAY)
                        .totalAmount(1000000000)
                        .orderNumber(orderNumber.getValue())
                        .expireTime(100)
                        .subject("苹果1斤")
                        .build();
        System.out.println(tradePreCreateRequest);
        System.out.println(payService.preCreate(tradePreCreateRequest));
    }

    @Test
    public void query() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void refund() {
    }
}