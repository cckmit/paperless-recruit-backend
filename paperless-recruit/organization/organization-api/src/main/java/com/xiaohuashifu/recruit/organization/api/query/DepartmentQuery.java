package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：部门查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
public class DepartmentQuery implements Serializable {

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
     * 部门编号
     */
    private Long id;

    /**
     * 部门编号列表
     */
    private List<Long> ids;

    /**
     * 部门所属组织编号
     */
    private Long organizationId;

    /**
     * 部门名，可模糊
     */
    private String departmentName;

    /**
     * 部门名缩写，可模糊
     */
    private String abbreviationDepartmentName;

    /**
     * 部门是否被废弃
     */
    private Boolean deactivated;

    /**
     * 按照部门是否被废弃排序
     */
    private Boolean orderByDeactivated;

    /**
     * 按照部门是否被废弃逆序排序
     */
    private Boolean orderByDeactivatedDesc;

    /**
     * 按照部门名排序
     */
    private Boolean orderByDepartmentName;

    /**
     * 按照部门名逆序排序
     */
    private Boolean orderByDepartmentNameDesc;

    /**
     * 按照部门名缩写排序
     */
    private Boolean orderByAbbreviationDepartmentName;

    /**
     * 按照部门名缩写逆序排序
     */
    private Boolean orderByAbbreviationDepartmentNameDesc;

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAbbreviationDepartmentName() {
        return abbreviationDepartmentName;
    }

    public void setAbbreviationDepartmentName(String abbreviationDepartmentName) {
        this.abbreviationDepartmentName = abbreviationDepartmentName;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
    }

    public Boolean getOrderByDeactivated() {
        return orderByDeactivated;
    }

    public void setOrderByDeactivated(Boolean orderByDeactivated) {
        this.orderByDeactivated = orderByDeactivated;
    }

    public Boolean getOrderByDeactivatedDesc() {
        return orderByDeactivatedDesc;
    }

    public void setOrderByDeactivatedDesc(Boolean orderByDeactivatedDesc) {
        this.orderByDeactivatedDesc = orderByDeactivatedDesc;
    }

    public Boolean getOrderByDepartmentName() {
        return orderByDepartmentName;
    }

    public void setOrderByDepartmentName(Boolean orderByDepartmentName) {
        this.orderByDepartmentName = orderByDepartmentName;
    }

    public Boolean getOrderByDepartmentNameDesc() {
        return orderByDepartmentNameDesc;
    }

    public void setOrderByDepartmentNameDesc(Boolean orderByDepartmentNameDesc) {
        this.orderByDepartmentNameDesc = orderByDepartmentNameDesc;
    }

    public Boolean getOrderByAbbreviationDepartmentName() {
        return orderByAbbreviationDepartmentName;
    }

    public void setOrderByAbbreviationDepartmentName(Boolean orderByAbbreviationDepartmentName) {
        this.orderByAbbreviationDepartmentName = orderByAbbreviationDepartmentName;
    }

    public Boolean getOrderByAbbreviationDepartmentNameDesc() {
        return orderByAbbreviationDepartmentNameDesc;
    }

    public void setOrderByAbbreviationDepartmentNameDesc(Boolean orderByAbbreviationDepartmentNameDesc) {
        this.orderByAbbreviationDepartmentNameDesc = orderByAbbreviationDepartmentNameDesc;
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
        return "DepartmentQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", organizationId=" + organizationId +
                ", departmentName='" + departmentName + '\'' +
                ", abbreviationDepartmentName='" + abbreviationDepartmentName + '\'' +
                ", deactivated=" + deactivated +
                ", orderByDeactivated=" + orderByDeactivated +
                ", orderByDeactivatedDesc=" + orderByDeactivatedDesc +
                ", orderByDepartmentName=" + orderByDepartmentName +
                ", orderByDepartmentNameDesc=" + orderByDepartmentNameDesc +
                ", orderByAbbreviationDepartmentName=" + orderByAbbreviationDepartmentName +
                ", orderByAbbreviationDepartmentNameDesc=" + orderByAbbreviationDepartmentNameDesc +
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
        private String departmentName;
        private String abbreviationDepartmentName;
        private Boolean deactivated;
        private Boolean orderByDeactivated;
        private Boolean orderByDeactivatedDesc;
        private Boolean orderByDepartmentName;
        private Boolean orderByDepartmentNameDesc;
        private Boolean orderByAbbreviationDepartmentName;
        private Boolean orderByAbbreviationDepartmentNameDesc;
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

        public Builder departmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public Builder abbreviationDepartmentName(String abbreviationDepartmentName) {
            this.abbreviationDepartmentName = abbreviationDepartmentName;
            return this;
        }

        public Builder deactivated(Boolean deactivated) {
            this.deactivated = deactivated;
            return this;
        }

        public Builder orderByDeactivated(Boolean orderByDeactivated) {
            this.orderByDeactivated = orderByDeactivated;
            return this;
        }

        public Builder orderByDeactivatedDesc(Boolean orderByDeactivatedDesc) {
            this.orderByDeactivatedDesc = orderByDeactivatedDesc;
            return this;
        }

        public Builder orderByDepartmentName(Boolean orderByDepartmentName) {
            this.orderByDepartmentName = orderByDepartmentName;
            return this;
        }

        public Builder orderByDepartmentNameDesc(Boolean orderByDepartmentNameDesc) {
            this.orderByDepartmentNameDesc = orderByDepartmentNameDesc;
            return this;
        }

        public Builder orderByAbbreviationDepartmentName(Boolean orderByAbbreviationDepartmentName) {
            this.orderByAbbreviationDepartmentName = orderByAbbreviationDepartmentName;
            return this;
        }

        public Builder orderByAbbreviationDepartmentNameDesc(Boolean orderByAbbreviationDepartmentNameDesc) {
            this.orderByAbbreviationDepartmentNameDesc = orderByAbbreviationDepartmentNameDesc;
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

        public DepartmentQuery build() {
            DepartmentQuery departmentQuery = new DepartmentQuery();
            departmentQuery.setPageNum(pageNum);
            departmentQuery.setPageSize(pageSize);
            departmentQuery.setId(id);
            departmentQuery.setIds(ids);
            departmentQuery.setOrganizationId(organizationId);
            departmentQuery.setDepartmentName(departmentName);
            departmentQuery.setAbbreviationDepartmentName(abbreviationDepartmentName);
            departmentQuery.setDeactivated(deactivated);
            departmentQuery.setOrderByDeactivated(orderByDeactivated);
            departmentQuery.setOrderByDeactivatedDesc(orderByDeactivatedDesc);
            departmentQuery.setOrderByDepartmentName(orderByDepartmentName);
            departmentQuery.setOrderByDepartmentNameDesc(orderByDepartmentNameDesc);
            departmentQuery.setOrderByAbbreviationDepartmentName(orderByAbbreviationDepartmentName);
            departmentQuery.setOrderByAbbreviationDepartmentNameDesc(orderByAbbreviationDepartmentNameDesc);
            departmentQuery.setOrderByCreateTime(orderByCreateTime);
            departmentQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            departmentQuery.setOrderByUpdateTime(orderByUpdateTime);
            departmentQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return departmentQuery;
        }
    }
}
