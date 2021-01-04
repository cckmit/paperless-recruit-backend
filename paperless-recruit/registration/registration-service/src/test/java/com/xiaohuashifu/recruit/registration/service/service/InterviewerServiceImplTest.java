package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewerService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void saveInterviewer() {
        System.out.println(interviewerService.saveInterviewer(1L, 2L));
    }

    @Test
    public void disableInterviewer() {
        System.out.println(interviewerService.disableInterviewer(1L));
    }

    @Test
    public void enableInterviewer() {
        System.out.println(interviewerService.enableInterviewer(1L));
    }

    @Test
    public void getOrganizationId() {
        System.out.println(interviewerService.getOrganizationId(1L));
    }

    @Test
    public void authenticatePrincipal() {
        System.out.println(interviewerService.authenticatePrincipal(1L, 21L));
    }
}