package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述：发送简单邮件参数对象
 *
 * @author xhsf
 * @create 2020/12/9 20:43
 */
public class SendSimpleEmailPO implements Serializable {

    /**
     * 目标邮箱
     */
    @NotBlank(message = "The email can't be blank.")
    @Email(message = "The email format error.")
    private String email;

    /**
     * 主题，也就是邮件的标题
     */
    @NotBlank(message = "The subject can't be blank.")
    @Size(max = EmailServiceConstants.MAX_EMAIL_SUBJECT_LENGTH,
            message = "The length of subject must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_SUBJECT_LENGTH + ".")
    private String subject;

    /**
     * 内容
     */
    @NotBlank(message = "The text can't be blank.")
    @Size(max = EmailServiceConstants.MAX_EMAIL_TEXT_LENGTH,
            message = "The length of text must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_TEXT_LENGTH + ".")
    private String text;

    /**
     * 附件
     */
    @Size(max = EmailServiceConstants.MAX_EMAIL_ATTACHMENT_NUMBER,
            message = "The length of attachmentMap must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_ATTACHMENT_NUMBER + ".")
    private Map<String, byte[]> attachmentMap;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, byte[]> getAttachmentMap() {
        return attachmentMap;
    }

    public void setAttachmentMap(Map<String, byte[]> attachmentMap) {
        this.attachmentMap = attachmentMap;
    }

    @Override
    public String toString() {
        return "SendSimpleEmailPO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachmentMap=" + attachmentMap +
                '}';
    }

    public static final class Builder {
        private String email;
        private String subject;
        private String text;
        private Map<String, byte[]> attachmentMap;

        public Builder email(String email) {
            this.email = email;
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

        public Builder attachmentMap(Map<String, byte[]> attachmentMap) {
            this.attachmentMap = attachmentMap;
            return this;
        }

        public SendSimpleEmailPO build() {
            SendSimpleEmailPO sendSimpleEmailPO = new SendSimpleEmailPO();
            sendSimpleEmailPO.setEmail(email);
            sendSimpleEmailPO.setSubject(subject);
            sendSimpleEmailPO.setText(text);
            sendSimpleEmailPO.setAttachmentMap(attachmentMap);
            return sendSimpleEmailPO;
        }
    }
}
