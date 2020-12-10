package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.constant.AppEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述：发送订阅消息的参数对象
 *
 * @author xhsf
 * @create 2020/12/9 17:46
 */
public class SendWeChatMpSubscribeMessagePO implements Serializable {
    /**
     * 微信小程序类型
     */
    @NotNull(message = "The app can't be null.")
    private AppEnum app;

    /**
     * 接收者（用户）的用户编号
     */
    @NotNull(message = "The userId can't be null.")
    @Positive(message = "The userId must be greater than 0.")
    private Long userId;

    /**
     * 所需下发的模板消息的 id
     */
    @NotBlank(message = "The templateId can't be blank.")
    private String templateId;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例 index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    @NotEmpty(message = "The templateData can't be empty.")
    private Map<String, SendWeChatMpSubscribeMessageDataPO> templateData;

    /**
     * 	跳转小程序类型：developer 为开发版；trial 为体验版；formal 为正式版；默认为正式版
     */
    private String mpType;

    /**
     * 进入小程序查看的语言类型，支持 zh_CN (简体中文)、en_US (英文)、zh_HK (繁体中文)、zh_TW (繁体中文)，默认为 zh_CN
     */
    private String language;

    public AppEnum getApp() {
        return app;
    }

    public void setApp(AppEnum app) {
        this.app = app;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, SendWeChatMpSubscribeMessageDataPO> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, SendWeChatMpSubscribeMessageDataPO> templateData) {
        this.templateData = templateData;
    }

    public String getMpType() {
        return mpType;
    }

    public void setMpType(String mpType) {
        this.mpType = mpType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "SendWeChatMpSubscribeMessagePO{" +
                "app=" + app +
                ", userId=" + userId +
                ", templateId='" + templateId + '\'' +
                ", page='" + page + '\'' +
                ", templateData=" + templateData +
                ", mpType='" + mpType + '\'' +
                ", language='" + language + '\'' +
                '}';
    }


    public static final class Builder {
        private AppEnum app;
        private Long userId;
        private String templateId;
        private String page;
        private Map<String, SendWeChatMpSubscribeMessageDataPO> templateData;
        private String mpType;
        private String language;

        public Builder app(AppEnum app) {
            this.app = app;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder page(String page) {
            this.page = page;
            return this;
        }

        public Builder templateData(Map<String, SendWeChatMpSubscribeMessageDataPO> templateData) {
            this.templateData = templateData;
            return this;
        }

        public Builder mpType(String mpType) {
            this.mpType = mpType;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public SendWeChatMpSubscribeMessagePO build() {
            SendWeChatMpSubscribeMessagePO sendWeChatMpSubscribeMessagePO = new SendWeChatMpSubscribeMessagePO();
            sendWeChatMpSubscribeMessagePO.setApp(app);
            sendWeChatMpSubscribeMessagePO.setUserId(userId);
            sendWeChatMpSubscribeMessagePO.setTemplateId(templateId);
            sendWeChatMpSubscribeMessagePO.setPage(page);
            sendWeChatMpSubscribeMessagePO.setTemplateData(templateData);
            sendWeChatMpSubscribeMessagePO.setMpType(mpType);
            sendWeChatMpSubscribeMessagePO.setLanguage(language);
            return sendWeChatMpSubscribeMessagePO;
        }
    }

}
