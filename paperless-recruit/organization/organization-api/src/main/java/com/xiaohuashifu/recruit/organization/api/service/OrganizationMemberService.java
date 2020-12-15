package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：组织成员服务
 *
 * @author xhsf
 * @create 2020/12/15 16:56
 */
public interface OrganizationMemberService {

    /**
     * 发送加入组织邀请
     *
     * @param organizationId 组织编号
     * @param username 用户名
     * @return 发送结果
     */
    Result<Void> sendInvitation(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The username can't be blank.") @Username String username);

}
