package com.xiaohuashifu.recruit.pay.service.manager.impl;

import com.xiaohuashifu.recruit.pay.service.PayServiceApplicationTests;
import com.xiaohuashifu.recruit.pay.service.dto.PreCreateDTO;
import com.xiaohuashifu.recruit.pay.service.dto.RefundDTO;
import com.xiaohuashifu.recruit.pay.service.manager.PayManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/6 21:16
 */

public class AlipayManagerImplTest extends PayServiceApplicationTests {

    @Autowired
    private PayManager payManager;

    @Test
    public void preCreate() {
        System.out.println(payManager.preCreate(PreCreateDTO.builder()
                .orderNumber("00000012020010714300100006")
                .subject("苹果21")
                .expireTime(90)
                .totalAmount(2)
                .build()));
    }

    @Test
    public void query() {
        System.out.println(payManager.query("00000012020010714300100001", null));
    }

    @Test
    public void cancel() {
        System.out.println(payManager.cancel("00000012020010714300100002", null));
    }

    @Test
    public void refund() {
        System.out.println(payManager.refund(RefundDTO.builder()
                .orderNumber("00000012020010714300100003")
                .refundAmount(100)
                .build()));
    }
}