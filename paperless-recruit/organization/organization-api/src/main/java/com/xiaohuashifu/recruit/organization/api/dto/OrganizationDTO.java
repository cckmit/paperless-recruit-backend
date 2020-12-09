package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织传输对象
 *
 * @author xhsf
 * @create 2020/12/6 21:11
 */
public class OrganizationDTO implements Serializable {

    /**
     * 组织编号
     */
    private Long id;

    /**
     * 组织所属主体编号
     */
    private Long userId;

    /**
     * 组织名
     */
    private String organizationName;

    /**
     * 组织名缩写
     */
    private String abbreviationOrganizationName;

    /**
     * 组织介绍
     */
    private String introduction;

    /**
     * 组织 logo
     */
    private String logoUrl;

    /**
     * 组织成员数
     */
    private String memberNumber;

    /**
     * 组织标签
     */
    private List<String> labels;

    /**
     * 组织是否有效
     */
    private Boolean available;

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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationName='" + organizationName + '\'' +
                ", abbreviationOrganizationName='" + abbreviationOrganizationName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                ", labels=" + labels +
                ", available=" + available +
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
        private List<String> labels;
        private Boolean available;

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

        public Builder labels(List<String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public OrganizationDTO build() {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            organizationDTO.setId(id);
            organizationDTO.setUserId(userId);
            organizationDTO.setOrganizationName(organizationName);
            organizationDTO.setAbbreviationOrganizationName(abbreviationOrganizationName);
            organizationDTO.setIntroduction(introduction);
            organizationDTO.setLogoUrl(logoUrl);
            organizationDTO.setMemberNumber(memberNumber);
            organizationDTO.setLabels(labels);
            organizationDTO.setAvailable(available);
            return organizationDTO;
        }
    }
}
