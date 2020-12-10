package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.DistributedLockService;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/10 19:24
 */
public class DistributedLockServiceImplTest {

    private DistributedLockService distributedLockService;

    @Before
    public void setUp() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("distributedLockServiceTest");
        ReferenceConfig<DistributedLockService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.DistributedLockService");
        reference.setApplication(application);
        reference.setInterface(DistributedLockService.class);
        reference.setTimeout(1000000);
        distributedLockService = reference.get();
    }

    @Test
    public void getLock() {
        System.out.println(distributedLockService.getLock("phone:15992321303"));
    }

    @Test
    public void testGetLock() {
        System.out.println(distributedLockService.getLock("phone:15992321303", 1000L, TimeUnit.SECONDS));
    }

    @Test
    public void releaseLock() {
        System.out.println(distributedLockService.releaseLock("phone:15992321303"));
    }
}