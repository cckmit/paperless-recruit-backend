package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:23
 */
public class SmsServiceImplTest {

    private SmsService smsService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("smsServiceTest");
        ReferenceConfig<SmsService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.SmsService");
        reference.setApplication(application);
        reference.setInterface(SmsService.class);
        smsService = reference.get();
    }

    @Test
    public void sendPhoneMessage() {
        final Result<Object> sendPhoneMessage = smsService.sendSms(
                new SmsDTO("15992321303", "zzzz"));
        System.out.println(sendPhoneMessage);
    }
}