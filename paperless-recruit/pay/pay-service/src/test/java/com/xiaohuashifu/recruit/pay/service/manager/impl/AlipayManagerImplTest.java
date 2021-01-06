package com.xiaohuashifu.recruit.pay.service.manager.impl;

import com.xiaohuashifu.recruit.pay.service.PayServiceApplicationTests;
import com.xiaohuashifu.recruit.pay.service.manager.AlipayManager;
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
    private AlipayManagerImpl alipayManager;

    @Test
    public void preCreate() {
        alipayManager.preCreate();
    }
}