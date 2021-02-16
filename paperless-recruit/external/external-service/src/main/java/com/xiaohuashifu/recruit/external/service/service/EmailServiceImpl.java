package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ThirdPartyServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.IncorrectValueServiceException;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import com.xiaohuashifu.recruit.external.api.request.CheckEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.SendSimpleEmailRequest;
import com.xiaohuashifu.recruit.external.api.request.SendTemplateEmailRequest;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述：发送邮件的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/18 16:19
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final StringRedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 邮箱验证码的Redis key前缀
     * 推荐格式为EMAIL_AUTH_CODE_REDIS_PREFIX:{subject}:{email}
     */
    private static final String EMAIL_AUTH_CODE_REDIS_PREFIX = "email:auth-code";

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine,
                            StringRedisTemplate redisTemplate) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendSimpleEmail(SendSimpleEmailRequest request) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(request.getEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getText());

            // 添加附件
            addAttachment(helper, request.getAttachmentMap());

            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            throw new ThirdPartyServiceException("Send email error.");
        }
    }

    @Override
    public void sendTemplateEmail(SendTemplateEmailRequest request) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(request.getEmail());
            helper.setSubject(request.getSubject());

            // 设置 text
            Context context = new Context();
            context.setVariables(request.getTemplateParameters());
            String text = templateEngine.process(request.getTemplateName(), context);
            helper.setText(text, true);

            // 添加附件
            addAttachment(helper, request.getAttachmentMap());

            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ThirdPartyServiceException("Send email error.", e);
        }
    }

    @Override
    public void createAndSendEmailAuthCode(CreateAndSendEmailAuthCodeRequest request) {
        // 构造发送邮件的参数
        String authCode = AuthCodeUtils.randomAuthCode();
        Map<String, Object> templateParameters = new HashMap<>();
        templateParameters.put("authCode", authCode);
        templateParameters.put("title", request.getTitle());
        templateParameters.put("expiredTime", request.getExpirationTime());
        String subject = "华农招新：" + request.getTitle() + "验证码";
        SendTemplateEmailRequest sendTemplateEmailPO = SendTemplateEmailRequest.builder().email(request.getEmail())
                .subject(subject).templateName("RecruitAuthCode").templateParameters(templateParameters)
                .attachmentMap(null).build();

        // 发送邮件到邮箱
        sendTemplateEmail(sendTemplateEmailPO);

        // 添加邮箱验证码到缓存
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX + ":" + request.getSubject() + ":" + request.getEmail();
        redisTemplate.opsForValue().set(redisKey, authCode, request.getExpirationTime(), TimeUnit.MINUTES);
    }

    @Override
    public void checkEmailAuthCode(CheckEmailAuthCodeRequest request) {
        // 从缓存取出验证码
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX + ":" + request.getSubject() + ":" + request.getEmail();
        String authCode = redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            throw new NotFoundServiceException("Auth code does not exist.");
        }

        // 验证码不正确
        if (!Objects.equals(authCode, request.getAuthCode())) {
            throw new IncorrectValueServiceException("Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (request.getDelete()) {
            redisTemplate.delete(redisKey);
        }
    }

    /**
     * 添加附件
     *
     * @param helper MimeMessageHelper
     * @param attachmentMap 附件
     */
    private void addAttachment(MimeMessageHelper helper, Map<String, byte[]> attachmentMap) throws MessagingException {
        // 如果附件为 null，不处理
        if (attachmentMap == null) {
            return;
        }

        // 添加附件
        for (Map.Entry<String, byte[]> attachment : attachmentMap.entrySet()) {
            // 不能为 null
            if (attachment == null) {
                continue;
            }

            // 不能大于限定大小
            if (attachment.getValue().length > EmailServiceConstants.MAX_EMAIL_ATTACHMENT_LENGTH) {
                continue;
            }

            helper.addAttachment(attachment.getKey(), new ByteArrayResource(attachment.getValue()));
        }
    }

}
