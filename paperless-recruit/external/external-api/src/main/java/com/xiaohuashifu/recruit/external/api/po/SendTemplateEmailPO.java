package com.xiaohuashifu.recruit.external.api.po;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Email
    private String to;

    /**
     * 主题
     */
    @NotBlank
    @Size(max = 78)
    private String subject;

    /**
     * 模板名
     */
    @NotBlank
    private String templateName;

    /**
     * 模板参数
     */
    @NotEmpty
    private Map<String, Object> templateParameters;

    /**
     * 附件
     */
    private Map<String, byte[]> attachmentMap;

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
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateParameters=" + templateParameters +
                ", attachmentMap=" + attachmentMap +
                '}';
    }

    public static final class Builder {
        private String to;
        private String subject;
        private String templateName;
        private Map<String, Object> templateParameters;
        private Map<String, byte[]> attachmentMap;

        public Builder to(String to) {
            this.to = to;
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
            sendTemplateEmailPO.setTo(to);
            sendTemplateEmailPO.setSubject(subject);
            sendTemplateEmailPO.setTemplateName(templateName);
            sendTemplateEmailPO.setTemplateParameters(templateParameters);
            sendTemplateEmailPO.setAttachmentMap(attachmentMap);
            return sendTemplateEmailPO;
        }
    }
}
