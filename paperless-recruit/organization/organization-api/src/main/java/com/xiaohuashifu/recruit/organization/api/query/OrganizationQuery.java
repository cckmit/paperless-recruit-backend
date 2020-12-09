package com.xiaohuashifu.recruit.organization.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
public class OrganizationQuery implements Serializable {

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
    private Long userId;
    private String organizationName;
    private String abbreviationOrganizationName;
    private String labelName;
    private Boolean available;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAbbreviationOrganizationName() {
        return abbreviationOrganizationName;
    }

    public void setAbbreviationOrganizationName(String abbreviationOrganizationName) {
        this.abbreviationOrganizationName = abbreviationOrganizationName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "OrganizationQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", userId=" + userId +
                ", organizationName='" + organizationName + '\'' +
                ", abbreviationOrganizationName='" + abbreviationOrganizationName + '\'' +
                ", labelName='" + labelName + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private Long userId;
        private String organizationName;
        private String abbreviationOrganizationName;
        private String labelName;
        private Boolean available;

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

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder abbreviationOrganizationName(String abbreviationOrganizationName) {
            this.abbreviationOrganizationName = abbreviationOrganizationName;
            return this;
        }

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public OrganizationQuery build() {
            OrganizationQuery organizationQuery = new OrganizationQuery();
            organizationQuery.setPageNum(pageNum);
            organizationQuery.setPageSize(pageSize);
            organizationQuery.setId(id);
            organizationQuery.setIds(ids);
            organizationQuery.setUserId(userId);
            organizationQuery.setOrganizationName(organizationName);
            organizationQuery.setAbbreviationOrganizationName(abbreviationOrganizationName);
            organizationQuery.setLabelName(labelName);
            organizationQuery.setAvailable(available);
            return organizationQuery;
        }
    }
}
