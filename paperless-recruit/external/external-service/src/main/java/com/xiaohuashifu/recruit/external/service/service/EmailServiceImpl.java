package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.EmailDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述：发送邮件的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/18 16:19
 */
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    private final RedisTemplate<Object, Object> redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine,
                            RedisTemplate<Object, Object> redisTemplate) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.redisTemplate = redisTemplate;
    }

    // TODO: 2020/11/18 目前不支持附件功能
    /**
     * 发送简单邮件
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败，可能是邮箱地址错误，或者网络延迟
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
            return Result.fail(ErrorCode.UNKNOWN_ERROR);
        }

        mailSender.send(mimeMessage);
        return Result.success();
    }


    // TODO: 2020/11/18 目前不支持附件功能
    /**
     * 发送模板邮件，使用的是velocity模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败，可能是邮箱地址错误，或者网络延迟
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
            return Result.fail(ErrorCode.UNKNOWN_ERROR);
        }

        mailSender.send(mimeMessage);
        return Result.success();
    }


    /**
     * 发送邮箱验证码服务
     * 该服务会把邮箱验证码进行缓存
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件验证码失败，可能是邮箱地址错误，或者网络延迟
     *
     * @param emailAuthCodeDTO 邮箱验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示发送成功，否则发送失败
     */
    @Override
    public Result<Void> createAndSendEmailAuthCode(EmailAuthCodeDTO emailAuthCodeDTO) {
        // 发送邮件到邮箱
        String authCode = AuthCodeUtils.randomAuthCode();
        Map<String, Object> model = new HashMap<>();
        model.put("authCode", authCode);
        model.put("title", emailAuthCodeDTO.getTitle());
        model.put("expiredTime", emailAuthCodeDTO.getExpiredTime());
        EmailDTO emailD = new EmailDTO.Builder()
                .to(emailAuthCodeDTO.getEmail())
                .subject("华农招新：" + emailAuthCodeDTO.getTitle() + "验证码")
                .build();
        Result<Void> sendEmailAuthCodeResult = sendTemplateEmail(
                emailD, "RecruitAuthCode", model, null);
        if (!sendEmailAuthCodeResult.isSuccess()) {
            return sendEmailAuthCodeResult;
        }

        // 添加邮箱验证码到缓存
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX
                + ":" + emailAuthCodeDTO.getSubject() + ":" + emailAuthCodeDTO.getEmail();
        redisTemplate.opsForValue().set(redisKey, authCode, emailAuthCodeDTO.getExpiredTime(), TimeUnit.MINUTES);

        return Result.success();
    }

    /**
     * 邮箱验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（EmailAuthCodeDTO.delete == true即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotFound: 找不到对应邮箱的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码值不正确
     *
     * @param emailAuthCodeDTO 邮箱验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    @Override
    public Result<Void> checkEmailAuthCode(@NotNull EmailAuthCodeDTO emailAuthCodeDTO) {
        // 从缓存取出验证码
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX
                + ":" + emailAuthCodeDTO.getSubject() + ":" + emailAuthCodeDTO.getEmail();
        String authCode = (String) redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_AUTH_CODE_NOT_FOUND, "Auth code does not exist.");
        }

        // 验证码不正确
        if (!authCode.equals(emailAuthCodeDTO.getAuthCode())) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (emailAuthCodeDTO.getDelete()) {
            redisTemplate.delete(redisKey);
        }

        return Result.success();
    }

}
