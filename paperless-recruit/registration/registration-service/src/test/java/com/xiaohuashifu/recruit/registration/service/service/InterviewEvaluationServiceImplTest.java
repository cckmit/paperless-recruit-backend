package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewEvaluationRequest;
import com.xiaohuashifu.recruit.registration.api.service.InterviewEvaluationService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/5 18:53
 */
public class InterviewEvaluationServiceImplTest {

    private InterviewEvaluationService interviewEvaluationService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("interviewEvaluationServiceTest");
        ReferenceConfig<InterviewEvaluationService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.InterviewEvaluationService");
        reference.setApplication(application);
        reference.setInterface(InterviewEvaluationService.class);
        reference.setTimeout(10000000);
        interviewEvaluationService = reference.get();
    }

    @Test
    public void saveInterviewEvaluation() {
        System.out.println(interviewEvaluationService.createInterviewEvaluation(CreateInterviewEvaluationRequest.builder()
                .interviewerId(2L)
                .interviewFormId(3L)
                .evaluation("过了")
                .build()));
    }

    @Test
    public void getInterviewEvaluation() {
        System.out.println(interviewEvaluationService.getInterviewEvaluation(1L));
    }

    @Test
    public void updateEvaluation() {

        System.out.println(interviewEvaluationService.updateEvaluation(1L, "这个人不错"));
    }

}