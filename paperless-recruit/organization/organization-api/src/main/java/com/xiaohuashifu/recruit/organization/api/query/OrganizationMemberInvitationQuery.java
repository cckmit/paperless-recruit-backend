package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：组织成员邀请查询参数
 *
 * @author xhsf
 * @create 2020/12/16 13:29
 */
public class OrganizationMemberInvitationQuery implements Serializable {

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
     * 邀请的状态
     */
    private OrganizationMemberInvitationStatusEnum invitationStatus;

    /**
     * 按照组织编号排序
     */
    private Boolean orderByOrganizationId;

    /**
     * 按照组织编号逆序排序
     */
    private Boolean orderByOrganizationIdDesc;

    /**
     * 按照邀请状态排序
     */
    private Boolean orderByInvitationStatus;

    /**
     * 按照邀请状态逆序排序
     */
    private Boolean orderByInvitationStatusDesc;

    /**
     * 按照邀请时间排序
     */
    private Boolean orderByInvitationTime;

    /**
     * 按照邀请时间逆序排序
     */
    private Boolean orderByInvitationTimeDesc;

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

    public OrganizationMemberInvitationStatusEnum getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(OrganizationMemberInvitationStatusEnum invitationStatus) {
        this.invitationStatus = invitationStatus;
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

    public Boolean getOrderByInvitationStatus() {
        return orderByInvitationStatus;
    }

    public void setOrderByInvitationStatus(Boolean orderByInvitationStatus) {
        this.orderByInvitationStatus = orderByInvitationStatus;
    }

    public Boolean getOrderByInvitationStatusDesc() {
        return orderByInvitationStatusDesc;
    }

    public void setOrderByInvitationStatusDesc(Boolean orderByInvitationStatusDesc) {
        this.orderByInvitationStatusDesc = orderByInvitationStatusDesc;
    }

    public Boolean getOrderByInvitationTime() {
        return orderByInvitationTime;
    }

    public void setOrderByInvitationTime(Boolean orderByInvitationTime) {
        this.orderByInvitationTime = orderByInvitationTime;
    }

    public Boolean getOrderByInvitationTimeDesc() {
        return orderByInvitationTimeDesc;
    }

    public void setOrderByInvitationTimeDesc(Boolean orderByInvitationTimeDesc) {
        this.orderByInvitationTimeDesc = orderByInvitationTimeDesc;
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
        return "OrganizationMemberInvitationQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", organizationId=" + organizationId +
                ", invitationStatus=" + invitationStatus +
                ", orderByOrganizationId=" + orderByOrganizationId +
                ", orderByOrganizationIdDesc=" + orderByOrganizationIdDesc +
                ", orderByInvitationStatus=" + orderByInvitationStatus +
                ", orderByInvitationStatusDesc=" + orderByInvitationStatusDesc +
                ", orderByInvitationTime=" + orderByInvitationTime +
                ", orderByInvitationTimeDesc=" + orderByInvitationTimeDesc +
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
        private OrganizationMemberInvitationStatusEnum invitationStatus;
        private Boolean orderByOrganizationId;
        private Boolean orderByOrganizationIdDesc;
        private Boolean orderByInvitationStatus;
        private Boolean orderByInvitationStatusDesc;
        private Boolean orderByInvitationTime;
        private Boolean orderByInvitationTimeDesc;
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

        public Builder invitationStatus(OrganizationMemberInvitationStatusEnum invitationStatus) {
            this.invitationStatus = invitationStatus;
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

        public Builder orderByInvitationStatus(Boolean orderByInvitationStatus) {
            this.orderByInvitationStatus = orderByInvitationStatus;
            return this;
        }

        public Builder orderByInvitationStatusDesc(Boolean orderByInvitationStatusDesc) {
            this.orderByInvitationStatusDesc = orderByInvitationStatusDesc;
            return this;
        }

        public Builder orderByInvitationTime(Boolean orderByInvitationTime) {
            this.orderByInvitationTime = orderByInvitationTime;
            return this;
        }

        public Builder orderByInvitationTimeDesc(Boolean orderByInvitationTimeDesc) {
            this.orderByInvitationTimeDesc = orderByInvitationTimeDesc;
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

        public OrganizationMemberInvitationQuery build() {
            OrganizationMemberInvitationQuery organizationMemberInvitationQuery = new OrganizationMemberInvitationQuery();
            organizationMemberInvitationQuery.setPageNum(pageNum);
            organizationMemberInvitationQuery.setPageSize(pageSize);
            organizationMemberInvitationQuery.setOrganizationId(organizationId);
            organizationMemberInvitationQuery.setInvitationStatus(invitationStatus);
            organizationMemberInvitationQuery.setOrderByOrganizationId(orderByOrganizationId);
            organizationMemberInvitationQuery.setOrderByOrganizationIdDesc(orderByOrganizationIdDesc);
            organizationMemberInvitationQuery.setOrderByInvitationStatus(orderByInvitationStatus);
            organizationMemberInvitationQuery.setOrderByInvitationStatusDesc(orderByInvitationStatusDesc);
            organizationMemberInvitationQuery.setOrderByInvitationTime(orderByInvitationTime);
            organizationMemberInvitationQuery.setOrderByInvitationTimeDesc(orderByInvitationTimeDesc);
            organizationMemberInvitationQuery.setOrderByCreateTime(orderByCreateTime);
            organizationMemberInvitationQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            organizationMemberInvitationQuery.setOrderByUpdateTime(orderByUpdateTime);
            organizationMemberInvitationQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return organizationMemberInvitationQuery;
        }
    }
}
