package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：部门传输对象
 *
 * @author xhsf
 * @create 2020/12/6 21:11
 */
public class DepartmentDTO implements Serializable {

    /**
     * 部门编号
     */
    private Long id;

    /**
     * 部门所属组织编号
     */
    private Long organizationId;

    /**
     * 部门名
     */
    private String departmentName;

    /**
     * 部门名缩写
     */
    private String abbreviationDepartmentName;

    /**
     * 部门介绍
     */
    private String introduction;

    /**
     * 部门 logo
     */
    private String logoUrl;

    /**
     * 部门成员数
     */
    private String memberNumber;

    /**
     * 部门标签
     */
    private List<String> labels;

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

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", departmentName='" + departmentName + '\'' +
                ", abbreviationDepartmentName='" + abbreviationDepartmentName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                ", labels=" + labels +
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

        public DepartmentDTO build() {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(id);
            departmentDTO.setOrganizationId(organizationId);
            departmentDTO.setDepartmentName(departmentName);
            departmentDTO.setAbbreviationDepartmentName(abbreviationDepartmentName);
            departmentDTO.setIntroduction(introduction);
            departmentDTO.setLogoUrl(logoUrl);
            departmentDTO.setMemberNumber(memberNumber);
            departmentDTO.setLabels(labels);
            return departmentDTO;
        }
    }
}
