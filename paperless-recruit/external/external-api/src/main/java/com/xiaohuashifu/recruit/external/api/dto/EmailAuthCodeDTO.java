package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.external.api.service.EmailService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：邮箱验证码，用于发送邮箱验证码时带的信息
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 13:42
 */
public class EmailAuthCodeDTO implements Serializable {
    @NotBlank
    @Email
    private String email;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时key的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    private String subject;

    /**
     * 标题，用于发送邮件验证码时标识该邮件验证码的目的
     * 推荐长度不超过10个汉字
     * 如“找回密码”，“邮箱绑定”等
     */
    @NotBlank(groups = EmailService.CreateAndSendEmailAuthCode.class)
    private String title;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     * 在调用EmailService.createAndSendEmailAuthCode()时需要带上
     */
    @NotNull(groups = EmailService.CreateAndSendEmailAuthCode.class)
    @Positive
    private Integer expiredTime;

    /**
     * 邮箱验证码
     * 在调用EmailService.checkEmailAuthCode()时需要带上
     */
    @NotBlank(groups = EmailService.CheckEmailAuthCode.class)
    @AuthCode
    private String authCode;

    /**
     * 检查成功后是否删除该键
     * 在调用EmailService.checkEmailAuthCode()时需要带上
     */
    @NotNull(groups = EmailService.CheckEmailAuthCode.class)
    private Boolean delete;


    public EmailAuthCodeDTO() {
    }

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

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "EmailAuthCodeDTO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", expiredTime=" + expiredTime +
                ", authCode='" + authCode + '\'' +
                ", delete=" + delete +
                '}';
    }


    public static final class Builder {
        private String email;
        private String subject;
        private String title;
        private Integer expiredTime;
        private String authCode;
        private Boolean delete;

        public Builder() {
        }

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

        public Builder expiredTime(Integer expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder delete(Boolean delete) {
            this.delete = delete;
            return this;
        }

        public EmailAuthCodeDTO build() {
            EmailAuthCodeDTO emailAuthCodeDTO = new EmailAuthCodeDTO();
            emailAuthCodeDTO.setEmail(email);
            emailAuthCodeDTO.setSubject(subject);
            emailAuthCodeDTO.setTitle(title);
            emailAuthCodeDTO.setExpiredTime(expiredTime);
            emailAuthCodeDTO.setAuthCode(authCode);
            emailAuthCodeDTO.setDelete(delete);
            return emailAuthCodeDTO;
        }
    }
}
