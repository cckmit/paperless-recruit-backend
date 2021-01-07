package com.xiaohuashifu.recruit.pay.api.request;

import com.xiaohuashifu.recruit.pay.api.domain.PaymentMethod;
import com.xiaohuashifu.recruit.pay.api.domain.*;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/7 16:39
 */
public class OrderPreCreateRequestTest {

    @Test
    public void getPaymentMethod() {
        BusinessNumber businessNumber = new BusinessNumber(1);
        CompactTime time = new CompactTime(LocalDateTime.now());
        OrderSerialNumber orderSerialNumber = new OrderSerialNumber("1");
        OrderNumber orderNumber = new OrderNumber(businessNumber, time, orderSerialNumber);
        Money totalAmount = new Money(100);
        TradeSubject subject = new TradeSubject("苹果1斤");
        TradeExpireTime orderExpireTime = new TradeExpireTime(100);
        TradePreCreateRequest orderPreCreateRequest = new TradePreCreateRequest(PaymentMethod.ALIPAY, orderNumber, totalAmount, subject, orderExpireTime);
        System.out.println(orderPreCreateRequest);
    }

    @Test
    public void getOrderNumber() {
    }

    @Test
    public void getTotalAmount() {
    }

    @Test
    public void getSubject() {
    }

    @Test
    public void getExpireTime() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void builder() {
    }
}