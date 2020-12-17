package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：组织成员查询参数
 *
 * @author xhsf
 * @create 2020/12/16 13:29
 */
public class OrganizationMemberQuery implements Serializable {

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
    private Long organizationId;

    /**
     * 部门编号
     */
    private Long departmentId;

    /**
     * 组织职位编号
     */
    private Long organizationPositionId;

    /**
     * 成员状态
     */
    private OrganizationMemberStatusEnum memberStatus;

    /**
     * 按照组织编号排序
     */
    private Boolean orderByOrganizationId;

    /**
     * 按照组织编号逆序排序
     */
    private Boolean orderByOrganizationIdDesc;

    /**
     * 按照部门编号排序
     */
    private Boolean orderByDepartmentId;

    /**
     * 按照部门编号逆序排序
     */
    private Boolean orderByDepartmentIdDesc;

    /**
     * 按照成员状态排序
     */
    private Boolean orderByMemberStatus;

    /**
     * 按照成员状态逆序排序
     */
    private Boolean orderByMemberStatusDesc;

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getOrganizationPositionId() {
        return organizationPositionId;
    }

    public void setOrganizationPositionId(Long organizationPositionId) {
        this.organizationPositionId = organizationPositionId;
    }

    public OrganizationMemberStatusEnum getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(OrganizationMemberStatusEnum memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Boolean getOrderByOrganizationId() {
        return orderByOrganizationId;
    }

    public void setOrderByOrganizationId(Boolean orderByOrganizationId) {
        this.orderByOrganizationId = orderByOrganizationId;
    }

    public Boolean getOrderByOrganizationIdDesc() {
        return orderByOrganizationIdDesc;
    }

    public void setOrderByOrganizationIdDesc(Boolean orderByOrganizationIdDesc) {
        this.orderByOrganizationIdDesc = orderByOrganizationIdDesc;
    }

    public Boolean getOrderByDepartmentId() {
        return orderByDepartmentId;
    }

    public void setOrderByDepartmentId(Boolean orderByDepartmentId) {
        this.orderByDepartmentId = orderByDepartmentId;
    }

    public Boolean getOrderByDepartmentIdDesc() {
        return orderByDepartmentIdDesc;
    }

    public void setOrderByDepartmentIdDesc(Boolean orderByDepartmentIdDesc) {
        this.orderByDepartmentIdDesc = orderByDepartmentIdDesc;
    }

    public Boolean getOrderByMemberStatus() {
        return orderByMemberStatus;
    }

    public void setOrderByMemberStatus(Boolean orderByMemberStatus) {
        this.orderByMemberStatus = orderByMemberStatus;
    }

    public Boolean getOrderByMemberStatusDesc() {
        return orderByMemberStatusDesc;
    }

    public void setOrderByMemberStatusDesc(Boolean orderByMemberStatusDesc) {
        this.orderByMemberStatusDesc = orderByMemberStatusDesc;
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
        return "OrganizationMemberQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", organizationId=" + organizationId +
                ", departmentId=" + departmentId +
                ", organizationPositionId=" + organizationPositionId +
                ", memberStatus=" + memberStatus +
                ", orderByOrganizationId=" + orderByOrganizationId +
                ", orderByOrganizationIdDesc=" + orderByOrganizationIdDesc +
                ", orderByDepartmentId=" + orderByDepartmentId +
                ", orderByDepartmentIdDesc=" + orderByDepartmentIdDesc +
                ", orderByMemberStatus=" + orderByMemberStatus +
                ", orderByMemberStatusDesc=" + orderByMemberStatusDesc +
                ", orderByCreateTime=" + orderByCreateTime +
                ", orderByCreateTimeDesc=" + orderByCreateTimeDesc +
                ", orderByUpdateTime=" + orderByUpdateTime +
                ", orderByUpdateTimeDesc=" + orderByUpdateTimeDesc +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long organizationId;
        private Long departmentId;
        private Long organizationPositionId;
        private OrganizationMemberStatusEnum memberStatus;
        private Boolean orderByOrganizationId;
        private Boolean orderByOrganizationIdDesc;
        private Boolean orderByDepartmentId;
        private Boolean orderByDepartmentIdDesc;
        private Boolean orderByMemberStatus;
        private Boolean orderByMemberStatusDesc;
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

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder organizationPositionId(Long organizationPositionId) {
            this.organizationPositionId = organizationPositionId;
            return this;
        }

        public Builder memberStatus(OrganizationMemberStatusEnum memberStatus) {
            this.memberStatus = memberStatus;
            return this;
        }

        public Builder orderByOrganizationId(Boolean orderByOrganizationId) {
            this.orderByOrganizationId = orderByOrganizationId;
            return this;
        }

        public Builder orderByOrganizationIdDesc(Boolean orderByOrganizationIdDesc) {
            this.orderByOrganizationIdDesc = orderByOrganizationIdDesc;
            return this;
        }

        public Builder orderByDepartmentId(Boolean orderByDepartmentId) {
            this.orderByDepartmentId = orderByDepartmentId;
            return this;
        }

        public Builder orderByDepartmentIdDesc(Boolean orderByDepartmentIdDesc) {
            this.orderByDepartmentIdDesc = orderByDepartmentIdDesc;
            return this;
        }

        public Builder orderByMemberStatus(Boolean orderByMemberStatus) {
            this.orderByMemberStatus = orderByMemberStatus;
            return this;
        }

        public Builder orderByMemberStatusDesc(Boolean orderByMemberStatusDesc) {
            this.orderByMemberStatusDesc = orderByMemberStatusDesc;
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

        public OrganizationMemberQuery build() {
            OrganizationMemberQuery organizationMemberQuery = new OrganizationMemberQuery();
            organizationMemberQuery.setPageNum(pageNum);
            organizationMemberQuery.setPageSize(pageSize);
            organizationMemberQuery.setOrganizationId(organizationId);
            organizationMemberQuery.setDepartmentId(departmentId);
            organizationMemberQuery.setOrganizationPositionId(organizationPositionId);
            organizationMemberQuery.setMemberStatus(memberStatus);
            organizationMemberQuery.setOrderByOrganizationId(orderByOrganizationId);
            organizationMemberQuery.setOrderByOrganizationIdDesc(orderByOrganizationIdDesc);
            organizationMemberQuery.setOrderByDepartmentId(orderByDepartmentId);
            organizationMemberQuery.setOrderByDepartmentIdDesc(orderByDepartmentIdDesc);
            organizationMemberQuery.setOrderByMemberStatus(orderByMemberStatus);
            organizationMemberQuery.setOrderByMemberStatusDesc(orderByMemberStatusDesc);
            organizationMemberQuery.setOrderByCreateTime(orderByCreateTime);
            organizationMemberQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            organizationMemberQuery.setOrderByUpdateTime(orderByUpdateTime);
            organizationMemberQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return organizationMemberQuery;
        }
    }
}
