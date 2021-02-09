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
    public void updateApplicationForm() throws IOException {
//        System.out.println(applicationFormService.updateApplicationForm(1L, ""));
    }

}