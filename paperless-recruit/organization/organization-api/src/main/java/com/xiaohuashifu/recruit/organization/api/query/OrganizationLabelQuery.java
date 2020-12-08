package com.xiaohuashifu.recruit.organization.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织标签查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
public class OrganizationLabelQuery implements Serializable {
    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;

    private Long id;
    private List<Long> ids;
    private String labelName;
    private Boolean available;

    private Boolean orderByAvailable;
    private Boolean orderByAvailableDesc;
    private Boolean orderByReferenceNumber;
    private Boolean orderByReferenceNumberDesc;
    private Boolean orderByLabelName;
    private Boolean orderByLabelNameDesc;
    private Boolean orderByCreateTime;
    private Boolean orderByCreateTimeDesc;
    private Boolean orderByUpdateTime;
    private Boolean orderByUpdateTimeDesc;

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

    public Boolean getOrderByAvailable() {
        return orderByAvailable;
    }

    public void setOrderByAvailable(Boolean orderByAvailable) {
        this.orderByAvailable = orderByAvailable;
    }

    public Boolean getOrderByAvailableDesc() {
        return orderByAvailableDesc;
    }

    public void setOrderByAvailableDesc(Boolean orderByAvailableDesc) {
        this.orderByAvailableDesc = orderByAvailableDesc;
    }

    public Boolean getOrderByReferenceNumber() {
        return orderByReferenceNumber;
    }

    public void setOrderByReferenceNumber(Boolean orderByReferenceNumber) {
        this.orderByReferenceNumber = orderByReferenceNumber;
    }

    public Boolean getOrderByReferenceNumberDesc() {
        return orderByReferenceNumberDesc;
    }

    public void setOrderByReferenceNumberDesc(Boolean orderByReferenceNumberDesc) {
        this.orderByReferenceNumberDesc = orderByReferenceNumberDesc;
    }

    public Boolean getOrderByLabelName() {
        return orderByLabelName;
    }

    public void setOrderByLabelName(Boolean orderByLabelName) {
        this.orderByLabelName = orderByLabelName;
    }

    public Boolean getOrderByLabelNameDesc() {
        return orderByLabelNameDesc;
    }

    public void setOrderByLabelNameDesc(Boolean orderByLabelNameDesc) {
        this.orderByLabelNameDesc = orderByLabelNameDesc;
    }

    public Boolean getOrderByCreateTime() {
        return orderByCreateTime;
    }

    public void setOrderByCreateTime(Boolean orderByCreateTime) {
        this.orderByCreateTime = orderByCreateTime;
    }

    public Boolean getOrderByCreateTimeDesc() {
        return orderByCreateTimeDesc;
    }

    public void setOrderByCreateTimeDesc(Boolean orderByCreateTimeDesc) {
        this.orderByCreateTimeDesc = orderByCreateTimeDesc;
    }

    public Boolean getOrderByUpdateTime() {
        return orderByUpdateTime;
    }

    public void setOrderByUpdateTime(Boolean orderByUpdateTime) {
        this.orderByUpdateTime = orderByUpdateTime;
    }

    public Boolean getOrderByUpdateTimeDesc() {
        return orderByUpdateTimeDesc;
    }

    public void setOrderByUpdateTimeDesc(Boolean orderByUpdateTimeDesc) {
        this.orderByUpdateTimeDesc = orderByUpdateTimeDesc;
    }

    @Override
    public String toString() {
        return "OrganizationLabelQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", labelName='" + labelName + '\'' +
                ", available=" + available +
                ", orderByAvailable=" + orderByAvailable +
                ", orderByAvailableDesc=" + orderByAvailableDesc +
                ", orderByReferenceNumber=" + orderByReferenceNumber +
                ", orderByReferenceNumberDesc=" + orderByReferenceNumberDesc +
                ", orderByLabelName=" + orderByLabelName +
                ", orderByLabelNameDesc=" + orderByLabelNameDesc +
                ", orderByCreateTime=" + orderByCreateTime +
                ", orderByCreateTimeDesc=" + orderByCreateTimeDesc +
                ", orderByUpdateTime=" + orderByUpdateTime +
                ", orderByUpdateTimeDesc=" + orderByUpdateTimeDesc +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private String labelName;
        private Boolean available;
        private Boolean orderByAvailable;
        private Boolean orderByAvailableDesc;
        private Boolean orderByReferenceNumber;
        private Boolean orderByReferenceNumberDesc;
        private Boolean orderByLabelName;
        private Boolean orderByLabelNameDesc;
        private Boolean orderByCreateTime;
        private Boolean orderByCreateTimeDesc;
        private Boolean orderByUpdateTime;
        private Boolean orderByUpdateTimeDesc;

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

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public Builder orderByAvailable(Boolean orderByAvailable) {
            this.orderByAvailable = orderByAvailable;
            return this;
        }

        public Builder orderByAvailableDesc(Boolean orderByAvailableDesc) {
            this.orderByAvailableDesc = orderByAvailableDesc;
            return this;
        }

        public Builder orderByReferenceNumber(Boolean orderByReferenceNumber) {
            this.orderByReferenceNumber = orderByReferenceNumber;
            return this;
        }

        public Builder orderByReferenceNumberDesc(Boolean orderByReferenceNumberDesc) {
            this.orderByReferenceNumberDesc = orderByReferenceNumberDesc;
            return this;
        }

        public Builder orderByLabelName(Boolean orderByLabelName) {
            this.orderByLabelName = orderByLabelName;
            return this;
        }

        public Builder orderByLabelNameDesc(Boolean orderByLabelNameDesc) {
            this.orderByLabelNameDesc = orderByLabelNameDesc;
            return this;
        }

        public Builder orderByCreateTime(Boolean orderByCreateTime) {
            this.orderByCreateTime = orderByCreateTime;
            return this;
        }

        public Builder orderByCreateTimeDesc(Boolean orderByCreateTimeDesc) {
            this.orderByCreateTimeDesc = orderByCreateTimeDesc;
            return this;
        }

        public Builder orderByUpdateTime(Boolean orderByUpdateTime) {
            this.orderByUpdateTime = orderByUpdateTime;
            return this;
        }

        public Builder orderByUpdateTimeDesc(Boolean orderByUpdateTimeDesc) {
            this.orderByUpdateTimeDesc = orderByUpdateTimeDesc;
            return this;
        }

        public OrganizationLabelQuery build() {
            OrganizationLabelQuery organizationLabelQuery = new OrganizationLabelQuery();
            organizationLabelQuery.setPageNum(pageNum);
            organizationLabelQuery.setPageSize(pageSize);
            organizationLabelQuery.setId(id);
            organizationLabelQuery.setIds(ids);
            organizationLabelQuery.setLabelName(labelName);
            organizationLabelQuery.setAvailable(available);
            organizationLabelQuery.setOrderByAvailable(orderByAvailable);
            organizationLabelQuery.setOrderByAvailableDesc(orderByAvailableDesc);
            organizationLabelQuery.setOrderByReferenceNumber(orderByReferenceNumber);
            organizationLabelQuery.setOrderByReferenceNumberDesc(orderByReferenceNumberDesc);
            organizationLabelQuery.setOrderByLabelName(orderByLabelName);
            organizationLabelQuery.setOrderByLabelNameDesc(orderByLabelNameDesc);
            organizationLabelQuery.setOrderByCreateTime(orderByCreateTime);
            organizationLabelQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            organizationLabelQuery.setOrderByUpdateTime(orderByUpdateTime);
            organizationLabelQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return organizationLabelQuery;
        }
    }
}
