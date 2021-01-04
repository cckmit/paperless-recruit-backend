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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.User.NotExist: 用户不存在
     *              Forbidden.User: 用户不可用
     *              OperationConflict: 该组织已经存在该成员
     *              OperationConflict.Duplicate: 已经发送了邀请，且邀请状态是等待接受
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *
     * @param organizationId 组织编号
     * @param username       用户名
     * @return 发送结果
     */
    Result<OrganizationMemberInvitationDTO> sendInvitation(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The username can't be blank.") @Username String username);

    /**
     * 接受加入组织邀请
     *
     * @permission 必须检查 organizationMemberInvitationId 是不是属于该用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *              OperationConflict.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 组织成员对象
     */
    Result<OrganizationMemberDTO> acceptInvitation(
            @NotNull(message = "The organizationMemberInvitationId can't be null.")
            @Positive(message = "The organizationMemberInvitationId must be greater than 0.")
                    Long organizationMemberInvitationId);

    /**
     * 查询组织成员
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织成员列表，可能返回空列表
     */
    Result<PageInfo<OrganizationMemberDTO>> listOrganizationMember(
            @NotNull(message = "The query can't be null.") OrganizationMemberQuery query);

    /**
     * 查询组织成员邀请
     *
     * @permission 只能查询组织自己的记录，也就是必须设置 organizationId
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationMemberInvitationDTO> 带分页参数的组织成员邀请列表，可能返回空列表
     */
    Result<PageInfo<OrganizationMemberInvitationDTO>> listOrganizationMemberInvitation(
            @NotNull(message = "The query can't be null.") OrganizationMemberInvitationQuery query);

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 组织成员编号
     * @return 组织编号，若组织成员不存在返回 null
     */
    Long getOrganizationId(Long id);

    /**
     * 获取用户编号
     *
     * @private 内部方法
     *
     * @param id 组织成员编号
     * @return 用户编号，若组织成员不存在返回 null
     */
    Long getUserId(Long id);

    /**
     * 更新组织成员的部门
     *
     * @permission 需要检查 organizationMemberId，newDepartmentId 是否属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧部门相同
     *              OperationConflict.Deactivated: 新部门已经被停用
     *
     * @param organizationMemberId 组织成员编号
     * @param newDepartmentId 部门编号，若为0表示不绑定任何部门
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateDepartment(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The newDepartmentId can't be null.")
            @PositiveOrZero(message = "The newDepartmentId must be greater than or equal to 0.") Long newDepartmentId);

    /**
     * 更新组织成员的组织职位
     *
     * @permission 需要检查 organizationMemberId 和 newOrganizationPositionId 是不是该组织的
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧组织职位相同
     *
     * @param organizationMemberId   组织成员编号
     * @param newOrganizationPositionId 组织职位编号，若为0表示不绑定任何组织职位
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateOrganizationPosition(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The newOrganizationPositionId can't be null.")
            @PositiveOrZero(message = "The newOrganizationPositionId must be greater than or equal to 0.")
                    Long newOrganizationPositionId);

    /**
     * 更新组织成员的状态
     *
     * @permission 需要检查 organizationMemberId 是否属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Unmodified: 新旧成员状态相同
     *
     * @param organizationMemberId 组织成员编号
     * @param newMemberStatus 成员状态
     * @return 更新部门后的组织成员对象
     */
    Result<OrganizationMemberDTO> updateMemberStatus(
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId,
            @NotNull(message = "The newMemberStatus can't be null.") OrganizationMemberStatusEnum newMemberStatus);

    /**
     * 清除组织成员的组织职位，该接口不对外开放
     * 即通过组织职位编号，是该组织职位编号的成员，设置组织职位为0
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param organizationPositionId 组织职位编号
     * @return 被清除职位的组织成员数量
     */
    Result<Integer> clearOrganizationPositions(
            @NotNull(message = "The organizationPositionId can't be null.")
            @PositiveOrZero(message = "The organizationPositionId must be greater than or equal to 0.")
                    Long organizationPositionId);

    /**
     * 拒绝加入组织邀请
     *
     * @permission 需要检查 organizationMemberInvitationId 是否属于该用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 拒绝结果
     */
    Result<OrganizationMemberInvitationDTO> rejectInvitation(
            @NotNull(message = "The organizationMemberInvitationId can't be null.")
            @Positive(message = "The organizationMemberInvitationId must be greater than 0.")
                    Long organizationMemberInvitationId);

    /**
     * 更新组织成员邀请的状态为 EXPIRED
     *
     * @private 内部方法
     *
     * @see com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum -> EXPIRED
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 更新结果
     */
    Result<OrganizationMemberInvitationDTO> updateInvitationStatusToExpired(
            @NotNull(message = "The organizationMemberInvitationId can't be null.")
            @Positive(message = "The organizationMemberInvitationId must be greater than 0.")
                    Long organizationMemberInvitationId);

}
