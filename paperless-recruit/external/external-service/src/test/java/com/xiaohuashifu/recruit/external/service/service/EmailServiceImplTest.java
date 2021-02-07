package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.request.CheckEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.SendSimpleEmailRequest;
import com.xiaohuashifu.recruit.external.api.request.SendTemplateEmailRequest;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/18 16:29
 */
public class EmailServiceImplTest {

    private EmailService emailService;

    @Before
    public void setUp() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("emailServiceTest");
        ReferenceConfig<EmailService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.EmailService");
        reference.setApplication(application);
        reference.setInterface(EmailService.class);
        reference.setTimeout(1000000);
        emailService = reference.get();
    }

    @Test
    public void sendSimpleEmail() throws IOException {

        File file = new File("D:\\Github\\paperless-recruit\\paperless-recruit-backend\\paperless-recruit\\external\\external-service\\src\\test\\java\\com\\xiaohuashifu\\recruit\\external\\service\\service\\EmailServiceImplTest.java");
        FileInputStream fileInputStream = new FileInputStream(file);
        Map<String, byte[]> attachmentMap = new HashMap<>();
        byte[] bytes = fileInputStream.readAllBytes();
        attachmentMap.put("hhx.jpg", bytes);
        emailService.sendSimpleEmail(SendSimpleEmailRequest.builder()
                .email("827032783@qq.com")
                .subject("邮箱测试")
                .text("测测测")
                .attachmentMap(attachmentMap)
                .build());
    }

    @Test
    public void sendTemplateEmail() throws IOException {
        Map<String, Object> templateParameters = new HashMap<>();
        templateParameters.put("authCode", "123456");
        templateParameters.put("title", "邮箱绑定");
        templateParameters.put("expiredTime", 10);

        Map<String, byte[]> attachmentMap = new HashMap<>();

        emailService.sendTemplateEmail(
                SendTemplateEmailRequest.builder()
                        .email("827032783@qq.com")
                        .subject("邮箱测试")
                        .templateName("RecruitAuthCode")
                        .templateParameters(templateParameters)
                        .attachmentMap(attachmentMap)
                        .build());
    }

    @Test
    public void createAndSendEmailAuthCode() {
        emailService.createAndSendEmailAuthCode(
                CreateAndSendEmailAuthCodeRequest.builder()
                        .email("827032783@qq.com").subject("email-update").title("邮箱绑定").expirationTime(5).build());
    }

    @Test
    public void checkEmailAuthCode() {
        emailService.checkEmailAuthCode(CheckEmailAuthCodeRequest.builder()
                .email("827032783@qq.com").subject("email-update").authCode("700285").delete(true).build());
    }
}