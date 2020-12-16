package com.xiaohuashifu.recruit.organization.api.dto;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：组织成员邀请传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
public class OrganizationMemberInvitationDTO implements Serializable {
    /**
     * 组织成员邀请编号
     */
    private Long id;

    /**
     * 发送邀请的组织
     */
    private Long organizationId;

    /**
     * 邀请的目标用户主体
     */
    private Long userId;

    /**
     * 邀请的时间
     */
    private LocalDateTime invitationTime;

    /**
     * @see OrganizationMemberInvitationStatusEnum
     * 邀请的状态
     */
    private String invitationStatus;

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

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @Override
    public String toString() {
        return "OrganizationMemberInvitationDTO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", userId=" + userId +
                ", invitationTime=" + invitationTime +
                ", invitationStatus='" + invitationStatus + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private Long userId;
        private LocalDateTime invitationTime;
        private String invitationStatus;

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

        public Builder invitationStatus(String invitationStatus) {
            this.invitationStatus = invitationStatus;
            return this;
        }

        public OrganizationMemberInvitationDTO build() {
            OrganizationMemberInvitationDTO organizationMemberInvitationDTO = new OrganizationMemberInvitationDTO();
            organizationMemberInvitationDTO.setId(id);
            organizationMemberInvitationDTO.setOrganizationId(organizationId);
            organizationMemberInvitationDTO.setUserId(userId);
            organizationMemberInvitationDTO.setInvitationTime(invitationTime);
            organizationMemberInvitationDTO.setInvitationStatus(invitationStatus);
            return organizationMemberInvitationDTO;
        }
    }
}
