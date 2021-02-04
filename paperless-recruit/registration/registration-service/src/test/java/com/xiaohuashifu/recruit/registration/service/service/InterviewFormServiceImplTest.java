package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.po.SaveInterviewFormPO;
import com.xiaohuashifu.recruit.registration.api.service.InterviewFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/5 16:06
 */
public class InterviewFormServiceImplTest {

    private InterviewFormService interviewFormService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("interviewFormServiceTest");
        ReferenceConfig<InterviewFormService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.InterviewFormService");
        reference.setApplication(application);
        reference.setInterface(InterviewFormService.class);
        reference.setTimeout(10000000);
        interviewFormService = reference.get();
    }

    @Test
    public void saveInterviewForm() {
        System.out.println(interviewFormService.saveInterviewForm(SaveInterviewFormPO.builder()
                .interviewId(9L)
                .applicationFormId(10L)
                .interviewLocation("创客空间")
                .interviewTime("1月6号下午 1点-3点")
                .note("带上个人作品")
                .build()));
    }

    @Test
    public void updateInterviewTime() {
        System.out.println(interviewFormService.updateInterviewTime(2L, "1月8号 晚上4-5点"));
    }

    @Test
    public void updateInterviewLocation() {
        System.out.println(interviewFormService.updateInterviewLocation(2L, "数信5楼"));
    }

    @Test
    public void updateNote() {
        System.out.println(interviewFormService.updateNote(3L, "带上电脑和纸币"));
    }

    @Test
    public void updateInterviewStatus() {
        System.out.println(interviewFormService.updateInterviewStatus(
                3L, InterviewStatusEnum.WAITING_INTERVIEW, InterviewStatusEnum.PASS));
    }

}