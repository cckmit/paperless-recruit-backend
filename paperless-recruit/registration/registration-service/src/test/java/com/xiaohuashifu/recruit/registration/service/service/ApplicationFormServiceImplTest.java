package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.po.*;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
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
    public void createApplicationForm() throws IOException {
        File file = new File("D:\\Github\\SpecializedCourseCode\\文档\\毕设相关文档\\图片\\毕设原型图片" +
                "\\u=3686185393,469536043&fm=26&gp=0.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        ApplicationFormAvatarPO applicationFormAvatarPO =
                ApplicationFormAvatarPO.builder().avatar(bytes).extensionName(".jpg").build();

        file = new File("D:\\Github\\DocumentAndCode\\MySQL\\druid连接池文档.md");
        fileInputStream = new FileInputStream(file);
        bytes = fileInputStream.readAllBytes();
        ApplicationFormAttachmentPO applicationFormAttachmentPO =
                ApplicationFormAttachmentPO.builder()
                .attachment(bytes)
                .attachmentName("druid连接池文档.md")
                .build();

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
                        .userId(5L)
        .recruitmentId(21L)
        .avatar(applicationFormAvatarPO)
        .fullName("吴嘉贤")
        .phone("13333333333")
        .firstDepartmentId(2L)
                        .secondDepartmentId(3L)
                        .email("827032783@qq.com")
                        .introduction("我是吴嘉贤")
        .attachment(applicationFormAttachmentPO)
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
        File file = new File("D:\\Github\\SpecializedCourseCode\\文档\\毕设相关文档\\图片\\毕设原型图片" +
                "\\u=336497710,1373556175&fm=26&gp=0.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        UpdateApplicationFormAvatarPO updateApplicationFormAvatarPO =
                UpdateApplicationFormAvatarPO.builder().id(6L).avatar(bytes).extensionName(".jpg").build();
        System.out.println(applicationFormService.updateAvatar(updateApplicationFormAvatarPO));
    }

    @Test
    public void updateFullName() {
        System.out.println(applicationFormService.updateFullName(8L, "呜呜呜"));
    }

    @Test
    public void updatePhone() {
        System.out.println(applicationFormService.updatePhone(8L, "15555555555"));
    }

    @Test
    public void updateFirstDepartment() {
        System.out.println(applicationFormService.updateFirstDepartment(8L, 3L));
    }

    @Test
    public void updateSecondDepartment() {
        System.out.println(applicationFormService.updateSecondDepartment(8L, 2L));
    }

    @Test
    public void updateEmail() {
        System.out.println(applicationFormService.updateEmail(8L, "888888888888@qq.com"));
    }

    @Test
    public void updateIntroduction() {
        System.out.println(applicationFormService.updateIntroduction(8L, "我是吴嘉贤"));
    }

    @Test
    public void updateAttachment() throws IOException {
        File file = new File("D:\\Github\\SpecializedCourseCode\\文档\\毕设相关文档\\图片\\系统整体架构.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        UpdateApplicationFormAttachmentPO updateApplicationFormAttachmentPO =
                UpdateApplicationFormAttachmentPO.builder()
                        .id(7L).attachment(bytes).attachmentName("系统整体架构1.png").build();
        System.out.println(applicationFormService.updateAttachment(updateApplicationFormAttachmentPO));
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