package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建并发送邮件验证码的参数对象
 *
 * @author: xhsf
 * @create: 2020/11/19 13:42
 */
public class CreateAndSendEmailAuthCodePO implements Serializable {

    /**
     * 邮箱
     */
    @NotBlank(message = "The email can't be blank.")
    @Email(message = "The email format error.")
    private String email;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank(message = "The subject can't be blank.")
    @Size(max = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH,
            message = "The length of subject must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH + ".")
    private String subject;

    /**
     * 标题，用于发送邮件验证码时标识该邮件验证码的目的
     * 如“找回密码”，“邮箱绑定”等
     */
    @NotBlank(message = "The title can't be blank.")
    @Size(max = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_TITLE_LENGTH,
            message = "The length of title must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_AUTH_CODE_TITLE_LENGTH + ".")
    private String title;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     */
    @NotNull(message = "The expirationTime can't be null.")
    @Positive(message = "The expirationTime must be greater than 0.")
    @Max(value = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_EXPIRATION_TIME,
            message = "The expirationTime must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_AUTH_CODE_EXPIRATION_TIME + ".")
    private Integer expirationTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "CreateAndSendEmailAuthCodePO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }

    public static final class Builder {
        private String email;
        private String subject;
        private String title;
        private Integer expirationTime;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder expirationTime(Integer expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public CreateAndSendEmailAuthCodePO build() {
            CreateAndSendEmailAuthCodePO createAndSendEmailAuthCodePO = new CreateAndSendEmailAuthCodePO();
            createAndSendEmailAuthCodePO.setEmail(email);
            createAndSendEmailAuthCodePO.setSubject(subject);
            createAndSendEmailAuthCodePO.setTitle(title);
            createAndSendEmailAuthCodePO.setExpirationTime(expirationTime);
            return createAndSendEmailAuthCodePO;
        }
    }

}
