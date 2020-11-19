package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.EmailDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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
    public void sendSimpleEmail() {
        System.out.println(emailService.sendSimpleEmail(new EmailDTO.Builder()
                .to("827032783@qq.com")
                .subject("邮箱测试")
                .text("测测测")
                .build(), null));
    }

    @Test
    public void sendTemplateEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put("authCode", "123456");
        model.put("subject", "邮箱绑定");
        model.put("validTime", 10);

        Map<String, FileSystemResource> attachmentMap = new HashMap<>();
        attachmentMap.put("test1", new FileSystemResource(new File("application.properties")));
        attachmentMap.put("test2", new FileSystemResource(new File("D:\\Github\\paperless-recruit\\paperless-recruit-backend\\paperless-recruit\\external\\external-service\\src\\main\\resources\\application.properties")));

        System.out.println(emailService.sendTemplateEmail(new EmailDTO.Builder()
                        .to("827032783@qq.com")
                        .subject("邮箱测试").build(),
                "RecruitAuthCode",
                model, attachmentMap));
    }

    @Test
    public void createAndSendEmailAuthCode() {
        System.out.println(emailService.createAndSendEmailAuthCode(
                new EmailAuthCodeDTO.Builder()
                        .email("859703569@qq.com").subject("email-update").title("邮箱绑定").expiredTime(5L).build()));
    }

    @Test
    public void checkEmailAuthCode() {
        System.out.println(emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email("827032783@qq.com").subject("email-update").authCode("401034").delete(true).build()));
    }
}