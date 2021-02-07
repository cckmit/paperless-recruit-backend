package com.xiaohuashifu.recruit.notification.service.service;

import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/16 0:48
 */
public class SystemNotificationServiceImplTest {

    private SystemNotificationService systemNotificationService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("systemNotificationServiceTest");
        ReferenceConfig<SystemNotificationService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20886/com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService");
        reference.setApplication(application);
        reference.setInterface(SystemNotificationService.class);
        reference.setTimeout(10000000);
        systemNotificationService = reference.get();
    }

    @Test
    public void sendSystemNotification() {
        System.out.println(systemNotificationService.sendSystemNotification(
                SendSystemNotificationRequest.builder()
                        .userId(1L)
                        .notificationTitle("校科联成员邀请")
                        .notificationType(SystemNotificationTypeEnum.ORGANIZATION_INVITATION)
                        .notificationContent("校科联邀请您成为他们的成员").build()));
    }

    @Test
    public void listSystemNotifications() {
        System.out.println(systemNotificationService.listSystemNotifications(
                SystemNotificationQuery.builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .userId(1L)
                        .build()));
    }

    @Test
    public void checkSystemNotification() {
        System.out.println(systemNotificationService.checkSystemNotification(1L));
    }

}