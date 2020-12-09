package com.xiaohuashifu.recruit.external.service.pojo.do0;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author: xhsf
 * @create: 2020/11/22 00:33
 */
public class WeChatMpSubscribeMessageTemplateDO {
    private Long id;

    /**
     * 模板编号
     */
    private String templateId;

    /**
     * 具体的微信小程序
     */
    private AppEnum appName;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 类目
     */
    private String templateType;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum templateStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppEnum getAppName() {
        return appName;
    }

    public void setAppName(AppEnum appName) {
        this.appName = appName;
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

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WeChatMpSubscribeMessageTemplateStatusEnum getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(WeChatMpSubscribeMessageTemplateStatusEnum templateStatus) {
        this.templateStatus = templateStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "WeChatMpSubscribeMessageTemplateDO{" +
                "id=" + id +
                ", templateId='" + templateId + '\'' +
                ", appName=" + appName +
                ", title='" + title + '\'' +
                ", templateType='" + templateType + '\'' +
                ", description='" + description + '\'' +
                ", templateStatus=" + templateStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String templateId;
        private AppEnum appName;
        private String title;
        private String templateType;
        private String description;
        private WeChatMpSubscribeMessageTemplateStatusEnum templateStatus;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder appName(AppEnum appName) {
            this.appName = appName;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder templateType(String templateType) {
            this.templateType = templateType;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder templateStatus(WeChatMpSubscribeMessageTemplateStatusEnum templateStatus) {
            this.templateStatus = templateStatus;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public WeChatMpSubscribeMessageTemplateDO build() {
            WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO = new WeChatMpSubscribeMessageTemplateDO();
            weChatMpSubscribeMessageTemplateDO.setId(id);
            weChatMpSubscribeMessageTemplateDO.setTemplateId(templateId);
            weChatMpSubscribeMessageTemplateDO.setAppName(appName);
            weChatMpSubscribeMessageTemplateDO.setTitle(title);
            weChatMpSubscribeMessageTemplateDO.setTemplateType(templateType);
            weChatMpSubscribeMessageTemplateDO.setDescription(description);
            weChatMpSubscribeMessageTemplateDO.setTemplateStatus(templateStatus);
            weChatMpSubscribeMessageTemplateDO.setCreateTime(createTime);
            weChatMpSubscribeMessageTemplateDO.setUpdateTime(updateTime);
            return weChatMpSubscribeMessageTemplateDO;
        }
    }
}
