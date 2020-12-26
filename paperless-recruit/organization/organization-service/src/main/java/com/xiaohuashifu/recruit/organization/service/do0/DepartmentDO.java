package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：部门
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class DepartmentDO {
    private Long id;
    private Long organizationId;
    private String departmentName;
    private String abbreviationDepartmentName;
    private String introduction;
    private String logoUrl;
    private String memberNumber;
    private List<String> labels;
    private Boolean deactivated;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
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
        return "DepartmentDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", departmentName='" + departmentName + '\'' +
                ", abbreviationDepartmentName='" + abbreviationDepartmentName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                ", labels=" + labels +
                ", deactivated=" + deactivated +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private String departmentName;
        private String abbreviationDepartmentName;
        private String introduction;
        private String logoUrl;
        private String memberNumber;
        private List<String> labels;
        private Boolean deactivated;
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

        public Builder departmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public Builder abbreviationDepartmentName(String abbreviationDepartmentName) {
            this.abbreviationDepartmentName = abbreviationDepartmentName;
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

        public Builder labels(List<String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder deactivated(Boolean deactivated) {
            this.deactivated = deactivated;
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

        public DepartmentDO build() {
            DepartmentDO departmentDO = new DepartmentDO();
            departmentDO.setId(id);
            departmentDO.setOrganizationId(organizationId);
            departmentDO.setDepartmentName(departmentName);
            departmentDO.setAbbreviationDepartmentName(abbreviationDepartmentName);
            departmentDO.setIntroduction(introduction);
            departmentDO.setLogoUrl(logoUrl);
            departmentDO.setMemberNumber(memberNumber);
            departmentDO.setLabels(labels);
            departmentDO.setDeactivated(deactivated);
            departmentDO.setCreateTime(createTime);
            departmentDO.setUpdateTime(updateTime);
            return departmentDO;
        }
    }
}
