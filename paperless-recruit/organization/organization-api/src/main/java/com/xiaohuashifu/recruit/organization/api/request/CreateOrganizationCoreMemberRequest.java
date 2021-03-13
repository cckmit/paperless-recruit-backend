package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationCoreMemberConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建组织核心成员请求
 *
 * @author xhsf
 * @create 2021/3/13 17:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationCoreMemberRequest implements Serializable {

    /**
     * 组织编号
     */
    @NotNull
    @Positive
    private Long organizationId;

    /**
     * 成员名
     */
    @NotBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_MEMBER_NAME_LENGTH)
    private String memberName;

    /**
     * 职位
     */
    @NotBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_POSITION_LENGTH)
    private String position;

    /**
     * 头像
     */
    @NotBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_AVATAR_URL_LENGTH)
    @Pattern(regexp = "(organizations/core-members/avartars)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)")
    private String avatarUrl;

    /**
     * 介绍
     */
    @NotBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_INTRODUCTION_LENGTH)
    private String introduction;

    /**
     * 职位优先级
     */
    @NotNull
    @Min(0)
    @Max(OrganizationCoreMemberConstants.MAX_INTRODUCTION_LENGTH)
    private Integer priority;

}
