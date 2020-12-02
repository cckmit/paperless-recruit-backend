package com.xiaohuashifu.recruit.external.api.query;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.TriStatusEnum;

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
public class WechatMpSubscribeMessageTemplateQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum;

    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;

    private Long id;

    private List<Long> idList;

    private AppEnum app;

    private String templateId;

    private String title;

    private String type;

    private String description;

    private TriStatusEnum status;

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

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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

    public TriStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TriStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WechatMpSubscribeMessageTemplateQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", idList=" + idList +
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
        private List<Long> idList;
        private AppEnum app;
        private String templateId;
        private String title;
        private String type;
        private String description;
        private TriStatusEnum status;

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

        public Builder idList(List<Long> idList) {
            this.idList = idList;
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

        public Builder status(TriStatusEnum status) {
            this.status = status;
            return this;
        }

        public WechatMpSubscribeMessageTemplateQuery build() {
            WechatMpSubscribeMessageTemplateQuery wechatMpSubscribeMessageTemplateQuery
                    = new WechatMpSubscribeMessageTemplateQuery();
            wechatMpSubscribeMessageTemplateQuery.setPageNum(pageNum);
            wechatMpSubscribeMessageTemplateQuery.setPageSize(pageSize);
            wechatMpSubscribeMessageTemplateQuery.setId(id);
            wechatMpSubscribeMessageTemplateQuery.setIdList(idList);
            wechatMpSubscribeMessageTemplateQuery.setApp(app);
            wechatMpSubscribeMessageTemplateQuery.setTemplateId(templateId);
            wechatMpSubscribeMessageTemplateQuery.setTitle(title);
            wechatMpSubscribeMessageTemplateQuery.setType(type);
            wechatMpSubscribeMessageTemplateQuery.setDescription(description);
            wechatMpSubscribeMessageTemplateQuery.setStatus(status);
            return wechatMpSubscribeMessageTemplateQuery;
        }
    }
}
