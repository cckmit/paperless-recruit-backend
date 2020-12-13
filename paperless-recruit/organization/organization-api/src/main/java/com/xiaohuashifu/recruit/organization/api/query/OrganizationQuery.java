package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

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
    @Max(value = QueryConstants.DEFAULT_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.DEFAULT_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 组织编号
     */
    private Long id;

    /**
     * 组织编号列表
     */
    private List<Long> ids;

    /**
     * 组织主体编号
     */
    private Long userId;

    /**
     * 组织名，可模糊
     */
    private String organizationName;

    /**
     * 组织名缩写，可模糊
     */
    private String abbreviationOrganizationName;

    /**
     * 组织是否可用
     */
    private Boolean available;

    /**
     * 按照组织是否可用排序
     */
    private Boolean orderByAvailable;

    /**
     * 按照组织是否可用逆序排序
     */
    private Boolean orderByAvailableDesc;

    /**
     * 按照组织名排序
     */
    private Boolean orderByOrganizationName;

    /**
     * 按照组织名逆序排序
     */
    private Boolean orderByOrganizationNameDesc;

    /**
     * 按照组织名缩写排序
     */
    private Boolean orderByAbbreviationOrganizationName;

    /**
     * 按照组织名缩写逆序排序
     */
    private Boolean orderByAbbreviationOrganizationNameDesc;

    /**
     * 按照创建时间排序
     */
    private Boolean orderByCreateTime;

    /**
     * 按照创建时间逆序排序
     */
    private Boolean orderByCreateTimeDesc;

    /**
     * 按照更新时间排序
     */
    private Boolean orderByUpdateTime;

    /**
     * 按照更新时间逆序排序
     */
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

    public Boolean getOrderByOrganizationName() {
        return orderByOrganizationName;
    }

    public void setOrderByOrganizationName(Boolean orderByOrganizationName) {
        this.orderByOrganizationName = orderByOrganizationName;
    }

    public Boolean getOrderByOrganizationNameDesc() {
        return orderByOrganizationNameDesc;
    }

    public void setOrderByOrganizationNameDesc(Boolean orderByOrganizationNameDesc) {
        this.orderByOrganizationNameDesc = orderByOrganizationNameDesc;
    }

    public Boolean getOrderByAbbreviationOrganizationName() {
        return orderByAbbreviationOrganizationName;
    }

    public void setOrderByAbbreviationOrganizationName(Boolean orderByAbbreviationOrganizationName) {
        this.orderByAbbreviationOrganizationName = orderByAbbreviationOrganizationName;
    }

    public Boolean getOrderByAbbreviationOrganizationNameDesc() {
        return orderByAbbreviationOrganizationNameDesc;
    }

    public void setOrderByAbbreviationOrganizationNameDesc(Boolean orderByAbbreviationOrganizationNameDesc) {
        this.orderByAbbreviationOrganizationNameDesc = orderByAbbreviationOrganizationNameDesc;
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
        return "OrganizationQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", userId=" + userId +
                ", organizationName='" + organizationName + '\'' +
                ", abbreviationOrganizationName='" + abbreviationOrganizationName + '\'' +
                ", available=" + available +
                ", orderByAvailable=" + orderByAvailable +
                ", orderByAvailableDesc=" + orderByAvailableDesc +
                ", orderByOrganizationName=" + orderByOrganizationName +
                ", orderByOrganizationNameDesc=" + orderByOrganizationNameDesc +
                ", orderByAbbreviationOrganizationName=" + orderByAbbreviationOrganizationName +
                ", orderByAbbreviationOrganizationNameDesc=" + orderByAbbreviationOrganizationNameDesc +
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
        private Long userId;
        private String organizationName;
        private String abbreviationOrganizationName;
        private Boolean available;
        private Boolean orderByAvailable;
        private Boolean orderByAvailableDesc;
        private Boolean orderByOrganizationName;
        private Boolean orderByOrganizationNameDesc;
        private Boolean orderByAbbreviationOrganizationName;
        private Boolean orderByAbbreviationOrganizationNameDesc;
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

        public Builder orderByOrganizationName(Boolean orderByOrganizationName) {
            this.orderByOrganizationName = orderByOrganizationName;
            return this;
        }

        public Builder orderByOrganizationNameDesc(Boolean orderByOrganizationNameDesc) {
            this.orderByOrganizationNameDesc = orderByOrganizationNameDesc;
            return this;
        }

        public Builder orderByAbbreviationOrganizationName(Boolean orderByAbbreviationOrganizationName) {
            this.orderByAbbreviationOrganizationName = orderByAbbreviationOrganizationName;
            return this;
        }

        public Builder orderByAbbreviationOrganizationNameDesc(Boolean orderByAbbreviationOrganizationNameDesc) {
            this.orderByAbbreviationOrganizationNameDesc = orderByAbbreviationOrganizationNameDesc;
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

        public OrganizationQuery build() {
            OrganizationQuery organizationQuery = new OrganizationQuery();
            organizationQuery.setPageNum(pageNum);
            organizationQuery.setPageSize(pageSize);
            organizationQuery.setId(id);
            organizationQuery.setIds(ids);
            organizationQuery.setUserId(userId);
            organizationQuery.setOrganizationName(organizationName);
            organizationQuery.setAbbreviationOrganizationName(abbreviationOrganizationName);
            organizationQuery.setAvailable(available);
            organizationQuery.setOrderByAvailable(orderByAvailable);
            organizationQuery.setOrderByAvailableDesc(orderByAvailableDesc);
            organizationQuery.setOrderByOrganizationName(orderByOrganizationName);
            organizationQuery.setOrderByOrganizationNameDesc(orderByOrganizationNameDesc);
            organizationQuery.setOrderByAbbreviationOrganizationName(orderByAbbreviationOrganizationName);
            organizationQuery.setOrderByAbbreviationOrganizationNameDesc(orderByAbbreviationOrganizationNameDesc);
            organizationQuery.setOrderByCreateTime(orderByCreateTime);
            organizationQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            organizationQuery.setOrderByUpdateTime(orderByUpdateTime);
            organizationQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return organizationQuery;
        }
    }
}
