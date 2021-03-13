package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationCoreMemberConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：更新组织核心成员请求
 *
 * @author xhsf
 * @create 2021/3/13 17:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationCoreMemberRequest implements Serializable {

    /**
     * 组织核心成员编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 成员名
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_MEMBER_NAME_LENGTH)
    private String memberName;

    /**
     * 职位
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_POSITION_LENGTH)
    private String position;

    /**
     * 头像
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_AVATAR_URL_LENGTH)
    @Pattern(regexp = "(organizations/core-members/avartars)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)")
    private String avatarUrl;

    /**
     * 介绍
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationCoreMemberConstants.MAX_INTRODUCTION_LENGTH)
    private String introduction;

    /**
     * 职位优先级
     */
    @Min(0)
    @Max(OrganizationCoreMemberConstants.MAX_INTRODUCTION_LENGTH)
    private Integer priority;

}
