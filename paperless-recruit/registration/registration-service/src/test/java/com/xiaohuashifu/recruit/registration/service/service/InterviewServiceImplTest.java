package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.service.InterviewService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/5 13:39
 */
public class InterviewServiceImplTest {

    private InterviewService interviewService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("interviewServiceTest");
        ReferenceConfig<InterviewService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.InterviewService");
        reference.setApplication(application);
        reference.setInterface(InterviewService.class);
        reference.setTimeout(10000000);
        interviewService = reference.get();
    }

    @Test
    public void createInterview() {
//        System.out.println(interviewService.createInterview(22L, "消息服务面试"));
    }

    @Test
    public void updateTitle() {
//        System.out.println(interviewService.updateInterview(5L, "自科部第wu轮面试"));
    }

    @Test
    public void getNextRound() {
        System.out.println(interviewService.getNextRound(3L));
    }

}