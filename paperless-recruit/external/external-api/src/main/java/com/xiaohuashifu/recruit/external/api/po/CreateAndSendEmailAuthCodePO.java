package com.xiaohuashifu.recruit.external.api.po;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建并发送邮件验证码的参数对象
 *
 * @author: xhsf
 * @create: 2020/11/19 13:42
 */
public class CreateAndSendEmailAuthCodePO implements Serializable {
    @NotBlank
    @Email
    private String email;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    private String subject;

    /**
     * 标题，用于发送邮件验证码时标识该邮件验证码的目的
     * 如“找回密码”，“邮箱绑定”等
     */
    @NotBlank
    @Size(max = 10)
    private String title;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     * 在调用 EmailService.createAndSendEmailAuthCode() 时需要带上
     */
    @NotNull
    @Positive
    @Max(10)
    private Integer expiredTime;

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

    @Override
    public String toString() {
        return "CreateAndSendEmailAuthCodePO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }

    public static final class Builder {
        private String email;
        private String subject;
        private String title;
        private Integer expiredTime;

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

        public CreateAndSendEmailAuthCodePO build() {
            CreateAndSendEmailAuthCodePO createAndSendEmailAuthCodePO = new CreateAndSendEmailAuthCodePO();
            createAndSendEmailAuthCodePO.setEmail(email);
            createAndSendEmailAuthCodePO.setSubject(subject);
            createAndSendEmailAuthCodePO.setTitle(title);
            createAndSendEmailAuthCodePO.setExpiredTime(expiredTime);
            return createAndSendEmailAuthCodePO;
        }
    }
}
