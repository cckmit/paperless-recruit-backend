package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：组织标签
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class OrganizationLabelDO {
    private Long id;
    private String labelName;
    private Long referenceNumber;
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
        return "OrganizationLabelDO{" +
                "id=" + id +
                ", labelName='" + labelName + '\'' +
                ", referenceNumber=" + referenceNumber +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String labelName;
        private Long referenceNumber;
        private Boolean available;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder referenceNumber(Long referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
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

        public OrganizationLabelDO build() {
            OrganizationLabelDO organizationLabelDO = new OrganizationLabelDO();
            organizationLabelDO.setId(id);
            organizationLabelDO.setLabelName(labelName);
            organizationLabelDO.setReferenceNumber(referenceNumber);
            organizationLabelDO.setAvailable(available);
            organizationLabelDO.setCreateTime(createTime);
            organizationLabelDO.setUpdateTime(updateTime);
            return organizationLabelDO;
        }
    }
}
