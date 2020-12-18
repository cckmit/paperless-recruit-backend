package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.FrequencyLimitService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/18 16:10
 */
public class FrequencyLimitServiceImplTest {
    private FrequencyLimitService frequencyLimitService;

    @Before
    public void setUp() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("frequencyLimitServiceTest");
        ReferenceConfig<FrequencyLimitService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.FrequencyLimitService");
        reference.setApplication(application);
        reference.setInterface(FrequencyLimitService.class);
        reference.setTimeout(1000000);
        frequencyLimitService = reference.get();
    }

    @Test
    public void isAllowed() {
        System.out.println(frequencyLimitService.isAllowed(
                "sms:auth-code:15992321301", 5L, 1L, TimeUnit.MINUTES));
    }
}