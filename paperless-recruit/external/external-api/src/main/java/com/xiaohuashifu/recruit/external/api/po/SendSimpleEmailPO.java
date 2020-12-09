package com.xiaohuashifu.recruit.external.api.po;

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
     * 内容
     */
    @NotBlank
    private String text;

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
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachmentMap=" + attachmentMap +
                '}';
    }

    public static final class Builder {
        private String to;
        private String subject;
        private String text;
        private Map<String, byte[]> attachmentMap;

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

        public Builder attachmentMap(Map<String, byte[]> attachmentMap) {
            this.attachmentMap = attachmentMap;
            return this;
        }

        public SendSimpleEmailPO build() {
            SendSimpleEmailPO sendSimpleEmailPO = new SendSimpleEmailPO();
            sendSimpleEmailPO.setTo(to);
            sendSimpleEmailPO.setSubject(subject);
            sendSimpleEmailPO.setText(text);
            sendSimpleEmailPO.setAttachmentMap(attachmentMap);
            return sendSimpleEmailPO;
        }
    }
}
