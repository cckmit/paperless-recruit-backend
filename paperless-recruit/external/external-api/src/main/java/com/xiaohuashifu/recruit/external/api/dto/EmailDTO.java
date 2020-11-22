package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.external.api.service.EmailService;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 描述：基本邮件传输对象
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/18 16:47
 */
public class EmailDTO implements Serializable {
    @NotBlank(groups = {EmailService.SendSimpleEmail.class, EmailService.SendTemplateEmail.class})
    @Email
    private String to;

    @NotBlank(groups = {EmailService.SendSimpleEmail.class, EmailService.SendTemplateEmail.class})
    private String subject;

    @NotBlank(groups = EmailService.SendSimpleEmail.class)
    private String text;

    public EmailDTO() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }


    public static final class Builder {
        private String to;
        private String subject;
        private String text;

        public Builder() {
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public EmailDTO build() {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(to);
            emailDTO.setSubject(subject);
            emailDTO.setText(text);
            return emailDTO;
        }
    }
}
