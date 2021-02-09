package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.service.InterviewerService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/4 22:26
 */
public class InterviewerServiceImplTest {

    private InterviewerService interviewerService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("interviewerServiceTest");
        ReferenceConfig<InterviewerService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.InterviewerService");
        reference.setApplication(application);
        reference.setInterface(InterviewerService.class);
        reference.setTimeout(10000000);
        interviewerService = reference.get();
    }

    @Test
    public void createInterviewer() {
//        System.out.println(interviewerService.createInterviewer(1L, 3L));
    }

    @Test
    public void getInterviewer() {
        System.out.println(interviewerService.getInterviewer(1L));
    }

    @Test
    public void disableInterviewer() {

        System.out.println(interviewerService.disableInterviewer(1L));
    }

    @Test
    public void enableInterviewer() {
        System.out.println(interviewerService.enableInterviewer(1L));
    }

}