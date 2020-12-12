package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：组织的标签映射
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class OrganizationOrganizationLabelDO {
    private Long id;
    private Long organizationId;
    private String labelName;
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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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
        return "OrganizationOrganizationLabelDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", labelName='" + labelName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private String labelName;
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

        public Builder labelName(String labelName) {
            this.labelName = labelName;
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

        public OrganizationOrganizationLabelDO build() {
            OrganizationOrganizationLabelDO organizationOrganizationLabelDO = new OrganizationOrganizationLabelDO();
            organizationOrganizationLabelDO.setId(id);
            organizationOrganizationLabelDO.setOrganizationId(organizationId);
            organizationOrganizationLabelDO.setLabelName(labelName);
            organizationOrganizationLabelDO.setCreateTime(createTime);
            organizationOrganizationLabelDO.setUpdateTime(updateTime);
            return organizationOrganizationLabelDO;
        }
    }
}
