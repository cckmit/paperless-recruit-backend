package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：组织
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class OrganizationDO {
    private Long id;
    private Long userId;
    private String organizationName;
    private String abbreviationOrganizationName;
    private String introduction;
    private String logoUrl;
    private String memberNumber;
    private Set<String> labels;
    private Boolean available;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAbbreviationOrganizationName() {
        return abbreviationOrganizationName;
    }

    public void setAbbreviationOrganizationName(String abbreviationOrganizationName) {
        this.abbreviationOrganizationName = abbreviationOrganizationName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
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
        return "OrganizationDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationName='" + organizationName + '\'' +
                ", abbreviationOrganizationName='" + abbreviationOrganizationName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                ", labels=" + labels +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String organizationName;
        private String abbreviationOrganizationName;
        private String introduction;
        private String logoUrl;
        private String memberNumber;
        private Set<String> labels;
        private Boolean available;
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

        public Builder organizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder abbreviationOrganizationName(String abbreviationOrganizationName) {
            this.abbreviationOrganizationName = abbreviationOrganizationName;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Builder memberNumber(String memberNumber) {
            this.memberNumber = memberNumber;
            return this;
        }

        public Builder labels(Set<String> labels) {
            this.labels = labels;
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

        public OrganizationDO build() {
            OrganizationDO organizationDO = new OrganizationDO();
            organizationDO.setId(id);
            organizationDO.setUserId(userId);
            organizationDO.setOrganizationName(organizationName);
            organizationDO.setAbbreviationOrganizationName(abbreviationOrganizationName);
            organizationDO.setIntroduction(introduction);
            organizationDO.setLogoUrl(logoUrl);
            organizationDO.setMemberNumber(memberNumber);
            organizationDO.setLabels(labels);
            organizationDO.setAvailable(available);
            organizationDO.setCreateTime(createTime);
            organizationDO.setUpdateTime(updateTime);
            return organizationDO;
        }
    }
}
