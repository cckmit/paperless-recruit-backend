package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.po.AddApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.po.UpdateApplicationFormTemplatePO;
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
                        .recruitmentId(14L)
                        .prompt("请各位报名者在附件里添加个人作品。")
                        .avatar(true)
                        .fullName(true)
                        .phone(true)
                        .firstDepartment(true)
                        .secondDepartment(true)
                        .email(true)
                        .introduction(false)
                        .attachment(true)
                        .studentNumber(true)
                        .college(true)
                        .major(true)
                        .note(true)
                        .build()));
    }

    @Test
    public void getApplicationFormTemplateByRecruitmentId() {
        System.out.println(applicationFormTemplateService.getApplicationFormTemplateByRecruitmentId(4L));
    }

    @Test
    public void updateApplicationFormTemplate() {
        System.out.println(applicationFormTemplateService.updateApplicationFormTemplate(
                UpdateApplicationFormTemplatePO.builder()
                .id(1L)
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
    public void updatePrompt() {
        System.out.println(applicationFormTemplateService.updatePrompt(1L, "各位加油噢，冲冲冲！"));
    }

    @Test
    public void deactivateApplicationFormTemplate() {
        System.out.println(applicationFormTemplateService.deactivateApplicationFormTemplate(1L));
    }

    @Test
    public void enableApplicationFormTemplate() {
        System.out.println(applicationFormTemplateService.enableApplicationFormTemplate(3L));
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