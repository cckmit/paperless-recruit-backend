package com.xiaohuashifu.recruit.organization.service.do0;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;

import java.time.LocalDateTime;

/**
 * 描述：组织成员数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
public class OrganizationMemberDO {
    private Long id;
    private Long userId;
    private Long organizationId;
    private Long departmentId;
    private Long organizationPositionId;
    private OrganizationMemberStatusEnum memberStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrganizationMemberDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationId=" + organizationId +
                ", departmentId=" + departmentId +
                ", organizationPositionId=" + organizationPositionId +
                ", memberStatus=" + memberStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Long organizationId;
        private Long departmentId;
        private Long organizationPositionId;
        private OrganizationMemberStatusEnum memberStatus;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public OrganizationMemberDO build() {
            OrganizationMemberDO organizationMemberDO = new OrganizationMemberDO();
            organizationMemberDO.setId(id);
            organizationMemberDO.setUserId(userId);
            organizationMemberDO.setOrganizationId(organizationId);
            organizationMemberDO.setDepartmentId(departmentId);
            organizationMemberDO.setOrganizationPositionId(organizationPositionId);
            organizationMemberDO.setMemberStatus(memberStatus);
            organizationMemberDO.setCreateTime(createTime);
            organizationMemberDO.setUpdateTime(updateTime);
            return organizationMemberDO;
        }
    }
}
