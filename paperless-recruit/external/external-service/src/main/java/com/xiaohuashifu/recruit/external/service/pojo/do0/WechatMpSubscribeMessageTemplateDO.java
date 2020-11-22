package com.xiaohuashifu.recruit.external.service.pojo.do0;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.constant.TriStatus;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 00:33
 */
@Alias("wechatMpSubscribeMessageTemplate")
public class WechatMpSubscribeMessageTemplateDO {
    private Long id;
    /**
     * 模板编号
     */
    private String templateId;

    /**
     * 具体的微信小程序
     */
    private App appName;

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
     * 状态
     */
    private TriStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public App getAppName() {
        return appName;
    }

    public void setAppName(App appName) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TriStatus getStatus() {
        return status;
    }

    public void setStatus(TriStatus status) {
        this.status = status;
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
        return "WechatMpSubscribeMessageTemplateDO{" +
                "id=" + id +
                ", templateId='" + templateId + '\'' +
                ", appName=" + appName +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String templateId;
        private App appName;
        private String title;
        private String type;
        private String description;
        private TriStatus status;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder appName(App appName) {
            this.appName = appName;
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

        public Builder status(TriStatus status) {
            this.status = status;
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

        public WechatMpSubscribeMessageTemplateDO build() {
            WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO = new WechatMpSubscribeMessageTemplateDO();
            wechatMpSubscribeMessageTemplateDO.setId(id);
            wechatMpSubscribeMessageTemplateDO.setTemplateId(templateId);
            wechatMpSubscribeMessageTemplateDO.setAppName(appName);
            wechatMpSubscribeMessageTemplateDO.setTitle(title);
            wechatMpSubscribeMessageTemplateDO.setType(type);
            wechatMpSubscribeMessageTemplateDO.setDescription(description);
            wechatMpSubscribeMessageTemplateDO.setStatus(status);
            wechatMpSubscribeMessageTemplateDO.setCreateTime(createTime);
            wechatMpSubscribeMessageTemplateDO.setUpdateTime(updateTime);
            return wechatMpSubscribeMessageTemplateDO;
        }
    }
}
