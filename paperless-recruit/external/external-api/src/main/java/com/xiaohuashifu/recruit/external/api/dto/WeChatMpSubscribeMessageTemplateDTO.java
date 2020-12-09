package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;

import java.io.Serializable;

/**
 * 描述：微信订阅消息模板的传输对象
 *
 * @author: xhsf
 * @create: 2020/11/22 00:33
 */
public class WeChatMpSubscribeMessageTemplateDTO implements Serializable {

    /**
     * 模板编号，项目产生的
     */
    private Long id;

    /**
     * 模板所属微信小程序
     */
    private AppEnum app;

    /**
     * 模板编号，微信小程序平台的
     */
    private String templateId;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 类目
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 模板的状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppEnum getApp() {
        return app;
    }

    public void setApp(AppEnum app) {
        this.app = app;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public WeChatMpSubscribeMessageTemplateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WeChatMpSubscribeMessageTemplateStatusEnum status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WechatMpSubscribeMessageTemplateDTO{" +
                "id=" + id +
                ", app=" + app +
                ", templateId='" + templateId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public static final class Builder {
        private Long id;
        private AppEnum app;
        private String templateId;
        private String title;
        private String type;
        private String description;
        private WeChatMpSubscribeMessageTemplateStatusEnum status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder app(AppEnum app) {
            this.app = app;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(WeChatMpSubscribeMessageTemplateStatusEnum status) {
            this.status = status;
            return this;
        }

        public WeChatMpSubscribeMessageTemplateDTO build() {
            WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDTO
                    = new WeChatMpSubscribeMessageTemplateDTO();
            weChatMpSubscribeMessageTemplateDTO.setId(id);
            weChatMpSubscribeMessageTemplateDTO.setApp(app);
            weChatMpSubscribeMessageTemplateDTO.setTemplateId(templateId);
            weChatMpSubscribeMessageTemplateDTO.setTitle(title);
            weChatMpSubscribeMessageTemplateDTO.setType(type);
            weChatMpSubscribeMessageTemplateDTO.setDescription(description);
            weChatMpSubscribeMessageTemplateDTO.setStatus(status);
            return weChatMpSubscribeMessageTemplateDTO;
        }
    }
}
