package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.request.*;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/30 16:42
 */
public class ApplicationFormServiceImplTest {

    private ApplicationFormService applicationFormService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("applicationFormServiceTest");
        ReferenceConfig<ApplicationFormService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService");
        reference.setApplication(application);
        reference.setInterface(ApplicationFormService.class);
        reference.setTimeout(10000000);
        applicationFormService = reference.get();
    }

    @Test
    public void createApplicationForm() {
//        userId
//                recruitmentId
//        avatar
//                fullName
//        phone
//                firstDepartmentId
//        secondDepartmentId
//                email
//        introduction
//                attachment
//        studentNumber
//                collegeId
//        majorId
//                note
        System.out.println(applicationFormService.createApplicationForm(
                CreateApplicationFormRequest.builder()
                        .userId(21L)
        .recruitmentId(22L)
        .avatarUrl("")
        .fullName("吴嘉贤")
        .phone("13333333333")
        .firstDepartmentId(2L)
                        .secondDepartmentId(4L)
                        .email("827032783@qq.com")
                        .introduction("我是吴嘉贤")
        .attachmentUrl("")
                        .studentNumber("201734020124")
                        .collegeId(1L)
                        .majorId(3L)
                        .note("请选我")
        .build()));
    }

    @Test
    public void getApplicationForm() {
        System.out.println(applicationFormService.getApplicationForm(1L));
    }

    @Test
    public void updateAvatar() throws IOException {
        System.out.println(applicationFormService.updateAvatar(1L, ""));
    }

    @Test
    public void updateFullName() {
        System.out.println(applicationFormService.updateFullName(10L, "呜333呜"));
    }

    @Test
    public void updatePhone() {
        System.out.println(applicationFormService.updatePhone(8L, "15555555555"));
    }

    @Test
    public void updateFirstDepartment() {
        System.out.println(applicationFormService.updateFirstDepartment(9L, 4L));
    }

    @Test
    public void updateSecondDepartment() {
        System.out.println(applicationFormService.updateSecondDepartment(10L, 2L));
    }

    @Test
    public void updateEmail() {
        System.out.println(applicationFormService.updateEmail(10L, "888888888888@qq.com"));
    }

    @Test
    public void updateIntroduction() {
        System.out.println(applicationFormService.updateIntroduction(8L, "我是吴嘉贤"));
    }

    @Test
    public void updateAttachment() throws IOException {
        System.out.println(applicationFormService.updateAttachment(1L, ""));
    }

    @Test
    public void updateStudentNumber() {
        System.out.println(applicationFormService.updateStudentNumber(8L, "201734020122"));
    }

    @Test
    public void updateCollege() {
        System.out.println(applicationFormService.updateCollege(8L, 2L));
    }

    @Test
    public void updateMajor() {
        System.out.println(applicationFormService.updateMajor(8L, 4L));
    }

    @Test
    public void updateNote() {
        System.out.println(applicationFormService.updateNote(8L, "啦啦啦"));
    }

    @Test
    public void getRecruitmentId() {
        System.out.println(applicationFormService.getRecruitmentId(8L));
    }

    @Test
    public void getUserId() {
        System.out.println(applicationFormService.getUserId(8L));
    }
}