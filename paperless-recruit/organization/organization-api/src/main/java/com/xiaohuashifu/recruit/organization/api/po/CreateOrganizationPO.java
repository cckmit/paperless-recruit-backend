package com.xiaohuashifu.recruit.organization.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;

import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：创建组织的参数
 *
 * @author xhsf
 * @create 2020/12/8 17:28
 */
public class CreateOrganizationPO {
    /**
     * 组织名
     */
    @NotBlank
    @Size(min = 2, max = 20)
    private String organizationName;

    /**
     * 组织名缩写
     */
    @NotBlank
    @Size(min = 2, max = 5)
    private String abbreviationOrganizationName;

    /**
     * 组织介绍
     */
    @NotBlank
    @Size(min = 1, max = 400)
    private String introduction;

    /**
     * 组织图标
     */
    @NotEmpty
    @Size(max = 10240)
    private byte[] logo;

    /**
     * 组织标签列表
     */
    @Size(max = 3)
    private List<String> labels;

    /**
     * 组织主体的邮箱，必须在该平台没有使用过
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 邮箱验证码
     */
    @NotBlank
    @AuthCode
    private String authCode;

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "CreateOrganizationPO{" +
                "organizationName='" + organizationName + '\'' +
                ", abbreviationOrganizationName='" + abbreviationOrganizationName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", labels=" + labels +
                ", email='" + email + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }


    public static final class Builder {
        String authCode;
        private String organizationName;
        private String abbreviationOrganizationName;
        private String introduction;
        private byte[] logo;
        private List<String> labels;
        private String email;

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

        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public Builder labels(List<String> labels) {
            this.labels = labels;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public CreateOrganizationPO build() {
            CreateOrganizationPO createOrganizationPO = new CreateOrganizationPO();
            createOrganizationPO.setOrganizationName(organizationName);
            createOrganizationPO.setAbbreviationOrganizationName(abbreviationOrganizationName);
            createOrganizationPO.setIntroduction(introduction);
            createOrganizationPO.setLogo(logo);
            createOrganizationPO.setLabels(labels);
            createOrganizationPO.setEmail(email);
            createOrganizationPO.setAuthCode(authCode);
            return createOrganizationPO;
        }
    }
}
