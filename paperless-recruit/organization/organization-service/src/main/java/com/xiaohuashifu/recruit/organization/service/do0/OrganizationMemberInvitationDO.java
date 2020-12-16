package com.xiaohuashifu.recruit.organization.service.do0;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;

import java.time.LocalDateTime;

/**
 * 描述：组织成员邀请数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
public class OrganizationMemberInvitationDO {
    private Long id;
    private Long organizationId;
    private Long userId;
    private LocalDateTime invitationTime;
    private OrganizationMemberInvitationStatusEnum invitationStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(LocalDateTime invitationTime) {
        this.invitationTime = invitationTime;
    }

    public OrganizationMemberInvitationStatusEnum getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(OrganizationMemberInvitationStatusEnum invitationStatus) {
        this.invitationStatus = invitationStatus;
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
        return "OrganizationMemberInvitationDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", userId=" + userId +
                ", invitationTime=" + invitationTime +
                ", invitationStatus=" + invitationStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private Long userId;
        private LocalDateTime invitationTime;
        private OrganizationMemberInvitationStatusEnum invitationStatus;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder invitationTime(LocalDateTime invitationTime) {
            this.invitationTime = invitationTime;
            return this;
        }

        public Builder invitationStatus(OrganizationMemberInvitationStatusEnum invitationStatus) {
            this.invitationStatus = invitationStatus;
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

        public OrganizationMemberInvitationDO build() {
            OrganizationMemberInvitationDO organizationMemberInvitationDO = new OrganizationMemberInvitationDO();
            organizationMemberInvitationDO.setId(id);
            organizationMemberInvitationDO.setOrganizationId(organizationId);
            organizationMemberInvitationDO.setUserId(userId);
            organizationMemberInvitationDO.setInvitationTime(invitationTime);
            organizationMemberInvitationDO.setInvitationStatus(invitationStatus);
            organizationMemberInvitationDO.setCreateTime(createTime);
            organizationMemberInvitationDO.setUpdateTime(updateTime);
            return organizationMemberInvitationDO;
        }
    }
}
