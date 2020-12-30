package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

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
public class OrganizationPositionQuery implements Serializable {

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
    @Max(value = QueryConstants.MAX_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.MAX_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 组织职位编号
     */
    private Long id;

    /**
     * 组织职位编号列表
     */
    private List<Long> ids;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 组织职位名，可模糊
     */
    private String positionName;

    /**
     * 职位优先级
     */
    private Integer priority;

    /**
     * 按照职位名排序
     */
    private Boolean orderByPositionName;

    /**
     * 按照职位名逆序排序
     */
    private Boolean orderByPositionNameDesc;

    /**
     * 按照职位优先级排序
     */
    private Boolean orderByPriority;

    /**
     * 按照标职位优先级逆序排序
     */
    private Boolean orderByPriorityDesc;

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getOrderByPositionName() {
        return orderByPositionName;
    }

    public void setOrderByPositionName(Boolean orderByPositionName) {
        this.orderByPositionName = orderByPositionName;
    }

    public Boolean getOrderByPositionNameDesc() {
        return orderByPositionNameDesc;
    }

    public void setOrderByPositionNameDesc(Boolean orderByPositionNameDesc) {
        this.orderByPositionNameDesc = orderByPositionNameDesc;
    }

    public Boolean getOrderByPriority() {
        return orderByPriority;
    }

    public void setOrderByPriority(Boolean orderByPriority) {
        this.orderByPriority = orderByPriority;
    }

    public Boolean getOrderByPriorityDesc() {
        return orderByPriorityDesc;
    }

    public void setOrderByPriorityDesc(Boolean orderByPriorityDesc) {
        this.orderByPriorityDesc = orderByPriorityDesc;
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
        return "OrganizationPositionQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", organizationId=" + organizationId +
                ", positionName='" + positionName + '\'' +
                ", priority=" + priority +
                ", orderByPositionName=" + orderByPositionName +
                ", orderByPositionNameDesc=" + orderByPositionNameDesc +
                ", orderByPriority=" + orderByPriority +
                ", orderByPriorityDesc=" + orderByPriorityDesc +
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
        private Long organizationId;
        private String positionName;
        private Integer priority;
        private Boolean orderByPositionName;
        private Boolean orderByPositionNameDesc;
        private Boolean orderByPriority;
        private Boolean orderByPriorityDesc;
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

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder orderByPositionName(Boolean orderByPositionName) {
            this.orderByPositionName = orderByPositionName;
            return this;
        }

        public Builder orderByPositionNameDesc(Boolean orderByPositionNameDesc) {
            this.orderByPositionNameDesc = orderByPositionNameDesc;
            return this;
        }

        public Builder orderByPriority(Boolean orderByPriority) {
            this.orderByPriority = orderByPriority;
            return this;
        }

        public Builder orderByPriorityDesc(Boolean orderByPriorityDesc) {
            this.orderByPriorityDesc = orderByPriorityDesc;
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

        public OrganizationPositionQuery build() {
            OrganizationPositionQuery organizationPositionQuery = new OrganizationPositionQuery();
            organizationPositionQuery.setPageNum(pageNum);
            organizationPositionQuery.setPageSize(pageSize);
            organizationPositionQuery.setId(id);
            organizationPositionQuery.setIds(ids);
            organizationPositionQuery.setOrganizationId(organizationId);
            organizationPositionQuery.setPositionName(positionName);
            organizationPositionQuery.setPriority(priority);
            organizationPositionQuery.setOrderByPositionName(orderByPositionName);
            organizationPositionQuery.setOrderByPositionNameDesc(orderByPositionNameDesc);
            organizationPositionQuery.setOrderByPriority(orderByPriority);
            organizationPositionQuery.setOrderByPriorityDesc(orderByPriorityDesc);
            organizationPositionQuery.setOrderByCreateTime(orderByCreateTime);
            organizationPositionQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            organizationPositionQuery.setOrderByUpdateTime(orderByUpdateTime);
            organizationPositionQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return organizationPositionQuery;
        }
    }
}
