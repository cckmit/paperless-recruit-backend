package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述：发送简单邮件参数对象
 *
 * @author xhsf
 * @create 2020/12/9 20:43
 */
public class SendTemplateEmailPO implements Serializable {

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
     * 模板名
     */
    @NotBlank(message = "The templateName can't be blank.")
    private String templateName;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParameters;

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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateParameters() {
        return templateParameters;
    }

    public void setTemplateParameters(Map<String, Object> templateParameters) {
        this.templateParameters = templateParameters;
    }

    public Map<String, byte[]> getAttachmentMap() {
        return attachmentMap;
    }

    public void setAttachmentMap(Map<String, byte[]> attachmentMap) {
        this.attachmentMap = attachmentMap;
    }

    @Override
    public String toString() {
        return "SendTemplateEmailPO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateParameters=" + templateParameters +
                ", attachmentMap=" + attachmentMap +
                '}';
    }

    public static final class Builder {
        private String email;
        private String subject;
        private String templateName;
        private Map<String, Object> templateParameters;
        private Map<String, byte[]> attachmentMap;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public Builder templateParameters(Map<String, Object> templateParameters) {
            this.templateParameters = templateParameters;
            return this;
        }

        public Builder attachmentMap(Map<String, byte[]> attachmentMap) {
            this.attachmentMap = attachmentMap;
            return this;
        }

        public SendTemplateEmailPO build() {
            SendTemplateEmailPO sendTemplateEmailPO = new SendTemplateEmailPO();
            sendTemplateEmailPO.setEmail(email);
            sendTemplateEmailPO.setSubject(subject);
            sendTemplateEmailPO.setTemplateName(templateName);
            sendTemplateEmailPO.setTemplateParameters(templateParameters);
            sendTemplateEmailPO.setAttachmentMap(attachmentMap);
            return sendTemplateEmailPO;
        }
    }

}
