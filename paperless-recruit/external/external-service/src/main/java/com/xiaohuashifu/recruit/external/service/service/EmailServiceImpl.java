package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.EmailDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/18 16:19
 */
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * 发送简单邮件
     *
     * @param emailDTO 需要to、subject、text三个字段
     * @param attachmentMap 附件Map，可以为null
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSimpleEmail(EmailDTO emailDTO, Map<String, FileSystemResource> attachmentMap) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());
            helper.setText(emailDTO.getText());

            // 如果附件不为null，添加附件
            if (attachmentMap != null) {
                for (Map.Entry<String, FileSystemResource> attachment : attachmentMap.entrySet()) {
                    helper.addAttachment(attachment.getKey(), attachment.getValue());
                }
            }
        } catch (MessagingException e) {
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }

        mailSender.send(mimeMessage);
        return Result.success();
    }


    /**
     * 发送模板邮件，使用的是velocity模板
     *
     * @param emailDTO 需要to、subject两个字段
     * @param templateName 模板名，模板需要提前创建
     * @param model 模板内的动态绑定的变量
     * @param attachmentMap 附件Map，可以为null
     * @return 发送结果
     */
    @Override
    public Result<Void> sendTemplateEmail(EmailDTO emailDTO, String templateName, Map<String, Object> model,
                                          Map<String, FileSystemResource> attachmentMap) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(emailDTO.getTo());
            helper.setSubject(emailDTO.getSubject());

            // 设置text
            Context context = new Context();
            context.setVariables(model);
            String text = templateEngine.process(templateName, context);
            helper.setText(text, true);

            // 如果附件不为null，添加附件
            if (attachmentMap != null) {
                for (Map.Entry<String, FileSystemResource> attachment : attachmentMap.entrySet()) {
                    helper.addAttachment(attachment.getKey(), attachment.getValue());
                }
            }
        } catch (MessagingException e) {
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }

        mailSender.send(mimeMessage);
        return Result.success();
    }
}
