package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.EmailDTO;
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

        File file = new File("C:\\Users\\82703\\Desktop\\1068912bdede14ff37ad3b88c7d89b5.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        Map<String, byte[]> attachmentMap = new HashMap<>();
        byte[] bytes = fileInputStream.readAllBytes();
        attachmentMap.put("hhx.jpg", bytes);
        System.out.println(emailService.sendSimpleEmail(new EmailDTO.Builder()
                .to("859703569@qq.com")
                .subject("邮箱测试")
                .text("测测测")
                .build(), attachmentMap));
    }

    @Test
    public void sendTemplateEmail() throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("authCode", "123456");
        model.put("subject", "邮箱绑定");
        model.put("validTime", 10);

        Map<String, byte[]> attachmentMap = new HashMap<>();

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
                        .email("859703569@qq.com").subject("email-update").title("邮箱绑定").expiredTime(5).build()));
    }

    @Test
    public void checkEmailAuthCode() {
        System.out.println(emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email("").subject("email-update").authCode("401034").delete(true).build()));
    }
}