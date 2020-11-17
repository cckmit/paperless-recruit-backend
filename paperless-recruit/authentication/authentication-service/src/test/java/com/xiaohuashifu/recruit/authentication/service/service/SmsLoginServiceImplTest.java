package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:52
 */
public class SmsLoginServiceImplTest {

    private SmsLoginService smsLoginService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("smsLoginServiceTest");
        ReferenceConfig<SmsLoginService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20882/com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService");
        reference.setApplication(application);
        reference.setInterface(SmsLoginService.class);
        smsLoginService = reference.get();
    }

    @Test
    public void createMessageAuthCodeAndSend() {
        System.out.println(smsLoginService.createSmsAuthCodeAndSend("15992321303"));
    }

    @Test
    public void checkMessageAuthCode() {
    }
}