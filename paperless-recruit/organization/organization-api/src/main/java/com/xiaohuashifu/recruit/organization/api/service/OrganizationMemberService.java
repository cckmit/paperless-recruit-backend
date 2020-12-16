package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
    Result<OrganizationMemberInvitationDTO> sendInvitation(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The username can't be blank.") @Username String username);

    /**
     * 接受加入组织邀请
     *
     * @param organizationId 组织编号
     * @param userId 用户编号
     * @return 接受结果
     */
    Result<Void> acceptInvitation(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId);

    /**
     * 查询组织成员
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织成员列表，可能返回空列表
     */
    Result<PageInfo<OrganizationMemberDTO>> listOrganizationMemberDTO(
            @NotNull(message = "The query can't be null.") OrganizationMemberQuery query);

    /**
     * 查询组织成员邀请
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationMemberInvitationDTO> 带分页参数的组织成员邀请列表，可能返回空列表
     */
    Result<PageInfo<OrganizationMemberInvitationDTO>> listOrganizationMemberInvitationDTO(
            @NotNull(message = "The query can't be null.") OrganizationMemberInvitationQuery query);

    /**
     * 更新组织成员的部门
     *
     * @param organizationMemberId 组织成员编号
     * @param departmentId 部门编号，若为0表示不绑定任何部门
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateDepartment(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The departmentId can't be null.")
            @PositiveOrZero(message = "The departmentId must be greater than or equal to 0.") Long departmentId);

    /**
     * 更新组织成员的组织职位
     *
     * @param organizationMemberId 组织成员编号
     * @param organizationPositionId 组织职位编号，若为0表示不绑定任何组织职位
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateOrganizationPosition(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The organizationPositionId can't be null.")
            @PositiveOrZero(message = "The organizationPositionId must be greater than or equal to 0.")
                    Long organizationPositionId);

    /**
     * 更新组织成员的状态
     *
     * @param organizationMemberId 组织成员编号
     * @param memberStatus 成员状态
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateOrganizationPosition(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The memberStatus can't be null.") OrganizationMemberStatusEnum memberStatus);
}
