package com.xiaohuashifu.recruit.organization.api.dto;

import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织传输对象
 *
 * @author xhsf
 * @create 2020/12/6 21:11
 */
public class OrganizationDTO implements Serializable {
    private Long id;
    private Long userId;

    @NotBlank(groups = OrganizationService.CreateOrganization.class)
    @Size(min = 2, max = 20, groups = OrganizationService.CreateOrganization.class)
    private String organizationName;

    @NotBlank(groups = OrganizationService.CreateOrganization.class)
    @Size(min = 2, max = 5, groups = OrganizationService.CreateOrganization.class)
    private String abbreviationOrganizationName;

    @NotBlank(groups = OrganizationService.CreateOrganization.class)
    @Size(min = 1, max = 400, groups = OrganizationService.CreateOrganization.class)
    private String introduction;


    private String logoUrl;

    private String memberNumber;

    /**
     * 组织标签列表
     */
    @NotEmpty(groups = OrganizationService.CreateOrganization.class)
    private List<String> labels;

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
