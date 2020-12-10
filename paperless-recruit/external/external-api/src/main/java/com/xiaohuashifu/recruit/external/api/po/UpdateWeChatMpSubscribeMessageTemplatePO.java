package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateServiceConstants;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新 WeChatMpSubscribeMessageTemplate 的参数对象
 *
 * @author xhsf
 * @create 2020/12/9 17:34
 */
public class UpdateWeChatMpSubscribeMessageTemplatePO implements Serializable {

    /**
     * 模板编号，（项目数据库生成的）
     */
    @NotNull(message = "The id can't be null.")
    @Positive(message = "The id must be greater than 0.")
    private Long id;

    /**
     * 微信小程序类型
     */
    private AppEnum app;

    /**
     * 模板编号
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TEMPLATE_ID_LENGTH,
            message = "The length of templateId must not be greater than "
                    + WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TEMPLATE_ID_LENGTH + ".")
    private String templateId;

    /**
     * 模板标题，（微信小程序平台生成的）
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TITLE_LENGTH,
            message = "The length of title must not be greater than "
                    + WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TITLE_LENGTH + ".")
    private String title;

    /**
     * 类目
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TYPE_LENGTH,
            message = "The length of type must not be greater than "
                    + WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TYPE_LENGTH + ".")
    private String type;

    /**
     * 描述
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_DESCRIPTION_LENGTH,
            message = "The length of description must not be greater than "
                    + WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_DESCRIPTION_LENGTH + ".")
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

    public void setDescription(String description) {
        this.description = description;
    }

    public WeChatMpSubscribeMessageTemplateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WeChatMpSubscribeMessageTemplateStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateWeChatMpSubscribeMessageTemplatePO{" +
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

        public UpdateWeChatMpSubscribeMessageTemplatePO build() {
            UpdateWeChatMpSubscribeMessageTemplatePO updateWeChatMpSubscribeMessageTemplatePO =
                    new UpdateWeChatMpSubscribeMessageTemplatePO();
            updateWeChatMpSubscribeMessageTemplatePO.setId(id);
            updateWeChatMpSubscribeMessageTemplatePO.setApp(app);
            updateWeChatMpSubscribeMessageTemplatePO.setTemplateId(templateId);
            updateWeChatMpSubscribeMessageTemplatePO.setTitle(title);
            updateWeChatMpSubscribeMessageTemplatePO.setType(type);
            updateWeChatMpSubscribeMessageTemplatePO.setDescription(description);
            updateWeChatMpSubscribeMessageTemplatePO.setStatus(status);
            return updateWeChatMpSubscribeMessageTemplatePO;
        }
    }
}
