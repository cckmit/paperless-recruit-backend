package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 描述：更新组织请求
 *
 * @author xhsf
 * @create 2021/2/7 20:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRequest implements Serializable {

    /**
     * 组织编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 组织名
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_NAME_LENGTH)
    private String organizationName;

    /**
     * 组织介绍
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_INTRODUCTION_LENGTH)
    private String introduction;

    /**
     * 组织 logo
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_LOGO_URL_LENGTH)
    @Pattern(regexp = "(organizations/logos/)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)")
    private String logoUrl;

    /**
     * 组织类型
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_TYPE_LENGTH)
    private String organizationType;

    /**
     * 组织规模
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_SIZE_LENGTH)
    private String size;

    /**
     * 组织地址
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_ADDRESS_LENGTH)
    private String address;

    /**
     * 组织网址
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_WEBSITE_LENGTH)
    private String website;

    /**
     * 组织标签
     *
     * 标签最大长度4
     */
    @Size(max = OrganizationConstants.MAX_ORGANIZATION_LABEL_NUMBER)
    private Set<String> labels;

}
