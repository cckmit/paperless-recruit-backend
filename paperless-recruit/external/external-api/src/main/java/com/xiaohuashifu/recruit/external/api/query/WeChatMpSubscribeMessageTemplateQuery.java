package com.xiaohuashifu.recruit.external.api.query;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：微信订阅消息模板的查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class WeChatMpSubscribeMessageTemplateQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull(message = "The pageNum can't be null.")
    @Positive(message = "The pageNum must be greater than 0.")
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull(message = "The pageSize can't be null.")
    @Positive(message = "The pageSize must be greater than 0.")
    @Max(value = 50, message = "The pageSize must be less than or equal to 50.")
    private Long pageSize;

    private Long id;

    private List<Long> ids;

    private AppEnum app;

    private String templateId;

    private String title;

    private String type;

    private String description;

    private WeChatMpSubscribeMessageTemplateStatusEnum status;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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
        return "WechatMpSubscribeMessageTemplateQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", app=" + app +
                ", templateId='" + templateId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private AppEnum app;
        private String templateId;
        private String title;
        private String type;
        private String description;
        private WeChatMpSubscribeMessageTemplateStatusEnum status;

        public Builder pageNum(Long pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ids(List<Long> ids) {
            this.ids = ids;
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

        public WeChatMpSubscribeMessageTemplateQuery build() {
            WeChatMpSubscribeMessageTemplateQuery weChatMpSubscribeMessageTemplateQuery
                    = new WeChatMpSubscribeMessageTemplateQuery();
            weChatMpSubscribeMessageTemplateQuery.setPageNum(pageNum);
            weChatMpSubscribeMessageTemplateQuery.setPageSize(pageSize);
            weChatMpSubscribeMessageTemplateQuery.setId(id);
            weChatMpSubscribeMessageTemplateQuery.setIds(ids);
            weChatMpSubscribeMessageTemplateQuery.setApp(app);
            weChatMpSubscribeMessageTemplateQuery.setTemplateId(templateId);
            weChatMpSubscribeMessageTemplateQuery.setTitle(title);
            weChatMpSubscribeMessageTemplateQuery.setType(type);
            weChatMpSubscribeMessageTemplateQuery.setDescription(description);
            weChatMpSubscribeMessageTemplateQuery.setStatus(status);
            return weChatMpSubscribeMessageTemplateQuery;
        }
    }
}
