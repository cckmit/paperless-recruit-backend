package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.po.ApplicationFormAttachmentPO;
import com.xiaohuashifu.recruit.registration.api.po.ApplicationFormAvatarPO;
import com.xiaohuashifu.recruit.registration.api.po.CreateApplicationFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

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
    public void createApplicationForm() throws IOException {
        File file = new File("D:\\Github\\SpecializedCourseCode\\文档\\毕设相关文档\\图片\\毕设原型图片" +
                "\\u=3686185393,469536043&fm=26&gp=0.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        ApplicationFormAvatarPO applicationFormAvatarPO = new ApplicationFormAvatarPO(bytes, ".jpg");

        file = new File("D:\\Github\\DocumentAndCode\\MySQL\\druid连接池文档.md");
        fileInputStream = new FileInputStream(file);
        bytes = fileInputStream.readAllBytes();
        ApplicationFormAttachmentPO applicationFormAttachmentPO = new ApplicationFormAttachmentPO(bytes, "druid连接池文档.md");

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
                CreateApplicationFormPO.builder()
                        .userId(1L)
        .recruitmentId(8L)
        .avatar(applicationFormAvatarPO)
        .fullName("吴嘉贤")
        .phone("13333333333")
        .firstDepartmentId(2L)
        .attachment(applicationFormAttachmentPO)
        .build()));
    }

    @Test
    public void getApplicationForm() {
    }
}