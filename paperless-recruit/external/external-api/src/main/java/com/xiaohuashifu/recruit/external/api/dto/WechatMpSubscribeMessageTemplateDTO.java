package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.constant.TriStatus;
import com.xiaohuashifu.recruit.external.api.service.WechatMpSubscribeMessageTemplateService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 00:33
 */
public class WechatMpSubscribeMessageTemplateDTO implements Serializable {

    @NotNull(groups = WechatMpSubscribeMessageTemplateService.UpdateWechatMpSubscribeMessageTemplate.class)
    private Long id;

    /**
     * 模板编号
     */
    @NotBlank(groups = WechatMpSubscribeMessageTemplateService.SaveWechatMpSubscribeMessageTemplate.class)
    private String templateId;

    /**
     * 模板标题
     */
    @NotBlank(groups = WechatMpSubscribeMessageTemplateService.SaveWechatMpSubscribeMessageTemplate.class)
    private String title;

    /**
     * 类目
     */
    @NotBlank(groups = WechatMpSubscribeMessageTemplateService.SaveWechatMpSubscribeMessageTemplate.class)
    private String type;

    /**
     * 描述
     */
    @NotBlank(groups = WechatMpSubscribeMessageTemplateService.SaveWechatMpSubscribeMessageTemplate.class)
    private String description;

    /**
     * 模板的状态
     */
    @NotBlank(groups = WechatMpSubscribeMessageTemplateService.SaveWechatMpSubscribeMessageTemplate.class)
    private TriStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TriStatus getStatus() {
        return status;
    }

    public void setStatus(TriStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "WechatMpSubscribeMessageTemplateDTO{" +
                "id=" + id +
                ", templateId='" + templateId + '\'' +
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

        public WechatMpSubscribeMessageTemplateDTO build() {
            WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO = new WechatMpSubscribeMessageTemplateDTO();
            wechatMpSubscribeMessageTemplateDTO.setId(id);
            wechatMpSubscribeMessageTemplateDTO.setTemplateId(templateId);
            wechatMpSubscribeMessageTemplateDTO.setTitle(title);
            wechatMpSubscribeMessageTemplateDTO.setType(type);
            wechatMpSubscribeMessageTemplateDTO.setDescription(description);
            wechatMpSubscribeMessageTemplateDTO.setStatus(status);
            wechatMpSubscribeMessageTemplateDTO.setCreateTime(createTime);
            wechatMpSubscribeMessageTemplateDTO.setUpdateTime(updateTime);
            return wechatMpSubscribeMessageTemplateDTO;
        }
    }
}
