package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import com.xiaohuashifu.recruit.external.api.po.CheckEmailAuthCodePO;
import com.xiaohuashifu.recruit.external.api.po.CreateAndSendEmailAuthCodePO;
import com.xiaohuashifu.recruit.external.api.po.SendSimpleEmailPO;
import com.xiaohuashifu.recruit.external.api.po.SendTemplateEmailPO;
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

    /**
     * 发送简单邮件
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟
     *
     * @param sendSimpleEmailPO 发送简单邮件的参数对象
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSimpleEmail(SendSimpleEmailPO sendSimpleEmailPO) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(sendSimpleEmailPO.getEmail());
            helper.setSubject(sendSimpleEmailPO.getSubject());
            helper.setText(sendSimpleEmailPO.getText());

            // 添加附件
            addAttachment(helper, sendSimpleEmailPO.getAttachmentMap());

            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }

        return Result.success();
    }

    /**
     * 发送模板邮件，使用的是 velocity 模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟 | 模板不存在 | 模板参数错误等
     *
     * @param sendTemplateEmailPO 发送模板消息的参数对象
     * @return 发送结果
     */
    @Override
    public Result<Void> sendTemplateEmail(SendTemplateEmailPO sendTemplateEmailPO) {
        MimeMessageHelper helper;
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // 设置基本信息
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(sendTemplateEmailPO.getEmail());
            helper.setSubject(sendTemplateEmailPO.getSubject());

            // 设置 text
            Context context = new Context();
            context.setVariables(sendTemplateEmailPO.getTemplateParameters());
            String text = templateEngine.process(sendTemplateEmailPO.getTemplateName(), context);
            helper.setText(text, true);

            // 添加附件
            addAttachment(helper, sendTemplateEmailPO.getAttachmentMap());

            // 发送邮件
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }

        return Result.success();
    }

    /**
     * 发送邮箱验证码服务
     * 该服务会把邮箱验证码进行缓存
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param createAndSendEmailAuthCodePO 创建并发送邮箱验证码参数对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示发送成功，否则发送失败
     */
    @Override
    public Result<Void> createAndSendEmailAuthCode(CreateAndSendEmailAuthCodePO createAndSendEmailAuthCodePO) {
        // 构造发送邮件的参数
        String authCode = AuthCodeUtils.randomAuthCode();
        Map<String, Object> templateParameters = new HashMap<>();
        templateParameters.put("authCode", authCode);
        templateParameters.put("title", createAndSendEmailAuthCodePO.getTitle());
        templateParameters.put("expiredTime", createAndSendEmailAuthCodePO.getExpirationTime());
        String subject = "华农招新：" + createAndSendEmailAuthCodePO.getTitle() + "验证码";
        SendTemplateEmailPO sendTemplateEmailPO = new SendTemplateEmailPO.Builder()
                .email(createAndSendEmailAuthCodePO.getEmail())
                .subject(subject)
                .templateName("RecruitAuthCode")
                .templateParameters(templateParameters)
                .attachmentMap(null)
                .build();

        // 发送邮件到邮箱
        Result<Void> sendEmailAuthCodeResult = sendTemplateEmail(sendTemplateEmailPO);
        if (!sendEmailAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR, "Send email auth code failed.");
        }

        // 添加邮箱验证码到缓存
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX
                + ":" + createAndSendEmailAuthCodePO.getSubject() + ":" + createAndSendEmailAuthCodePO.getEmail();
        redisTemplate.opsForValue().set(
                redisKey, authCode, createAndSendEmailAuthCodePO.getExpirationTime(), TimeUnit.MINUTES);

        return Result.success();
    }

    /**
     * 邮箱验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（EmailAuthCodeDTO.delete == true 即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotExist: 找不到对应邮箱的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码值不正确
     *
     * @param checkEmailAuthCodePO 检查邮箱验证码参数对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示验证成功，否则验证失败
     */
    @Override
    public Result<Void> checkEmailAuthCode(CheckEmailAuthCodePO checkEmailAuthCodePO) {
        // 从缓存取出验证码
        String redisKey = EMAIL_AUTH_CODE_REDIS_PREFIX
                + ":" + checkEmailAuthCodePO.getSubject() + ":" + checkEmailAuthCodePO.getEmail();
        String authCode = redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_NOT_EXIST,
                    "Auth code does not exist.");
        }

        // 验证码不正确
        if (!Objects.equals(authCode, checkEmailAuthCodePO.getAuthCode())) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT,
                    "Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (checkEmailAuthCodePO.getDelete()) {
            redisTemplate.delete(redisKey);
        }

        return Result.success();
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
