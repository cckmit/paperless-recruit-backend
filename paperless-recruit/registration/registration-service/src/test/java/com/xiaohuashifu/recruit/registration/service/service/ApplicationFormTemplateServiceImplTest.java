package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.po.AddApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/29 15:35
 */
public class ApplicationFormTemplateServiceImplTest {

    private ApplicationFormTemplateService applicationFormTemplateService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("applicationFormTemplateServiceTest");
        ReferenceConfig<ApplicationFormTemplateService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService");
        reference.setApplication(application);
        reference.setInterface(ApplicationFormTemplateService.class);
        reference.setTimeout(10000000);
        applicationFormTemplateService = reference.get();
    }


    @Test
    public void addApplicationFormTemplate() {
//        private Long recruitmentId;
//        private String prompt;
//        private Boolean avatar;
//        private Boolean fullName;
//        private Boolean phone;
//        private Boolean firstDepartment;
//        private Boolean secondDepartment;
//        private Boolean email;
//        private Boolean introduction;
//        private Boolean attachment;
//        private Boolean studentNumber;
//        private Boolean college;
//        private Boolean major;
//        private Boolean note;
        System.out.println(applicationFormTemplateService.addApplicationFormTemplate(
                AddApplicationFormTemplatePO.builder()
                        .recruitmentId(8L)
                        .prompt("请各位报名者在附件里添加个人作品。")
                        .avatar(true)
                        .fullName(true)
                        .phone(true)
                        .firstDepartment(true)
                        .secondDepartment(false)
                        .email(false)
                        .introduction(false)
                        .attachment(true)
                        .studentNumber(false)
                        .college(false)
                        .major(false)
                        .note(false)
                        .build()));
    }

    @Test
    public void getApplicationFormTemplateByRecruitmentId() {
    }

    @Test
    public void updateApplicationFormTemplate() {
    }

    @Test
    public void updatePrompt() {
    }

    @Test
    public void deactivateApplicationFormTemplate() {
    }

    @Test
    public void enableApplicationFormTemplate() {
    }

    @Test
    public void getRecruitmentId() {
    }

    @Test
    public void checkApplicationFormTemplateStatusByRecruitmentId() {
    }

    @Test
    public void canRegistration() {
        System.out.println(applicationFormTemplateService.canRegistration(8L));
    }
}