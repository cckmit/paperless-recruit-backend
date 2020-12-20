package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：组织核心成员数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
public class OrganizationCoreMemberDO {

    private Long id;
    private Long organizationId;
    private Long organizationMemberId;
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

    public Long getOrganizationMemberId() {
        return organizationMemberId;
    }

    public void setOrganizationMemberId(Long organizationMemberId) {
        this.organizationMemberId = organizationMemberId;
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
        return "OrganizationCoreMemberDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", organizationMemberId=" + organizationMemberId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private Long organizationMemberId;
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

        public Builder organizationMemberId(Long organizationMemberId) {
            this.organizationMemberId = organizationMemberId;
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

        public OrganizationCoreMemberDO build() {
            OrganizationCoreMemberDO organizationCoreMemberDO = new OrganizationCoreMemberDO();
            organizationCoreMemberDO.setId(id);
            organizationCoreMemberDO.setOrganizationId(organizationId);
            organizationCoreMemberDO.setOrganizationMemberId(organizationMemberId);
            organizationCoreMemberDO.setCreateTime(createTime);
            organizationCoreMemberDO.setUpdateTime(updateTime);
            return organizationCoreMemberDO;
        }
    }
}
