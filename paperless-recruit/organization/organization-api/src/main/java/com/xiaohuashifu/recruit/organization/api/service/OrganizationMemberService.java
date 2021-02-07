package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationMemberRequest;

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
     * @permission 必须是该组织的主体用户
     *
     * @param organizationId 组织编号
     * @param username       用户名
     * @return 发送结果
     */
    OrganizationMemberInvitationDTO sendInvitation(
            @NotNull @Positive Long organizationId, @NotBlank @Username String username) throws ServiceException;

    /**
     * 接受加入组织邀请
     *
     * @permission 必须检查 organizationMemberInvitationId 是不是属于该用户
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 组织成员对象
     */
    OrganizationMemberDTO acceptInvitation(@NotNull @Positive Long organizationMemberInvitationId)
            throws ServiceException;

    /**
     * 获取组织成员
     *
     * @private 内部方法
     *
     * @param id 组织成员编号
     * @return OrganizationMemberDTO
     */
    OrganizationMemberDTO getOrganizationMember(Long id) throws NotFoundServiceException;

    /**
     * 获取组织成员邀请
     *
     * @param id 组织成员邀请编号
     * @return OrganizationMemberInvitationDTO
     */
    OrganizationMemberInvitationDTO getOrganizationMemberInvitation(Long id) throws NotFoundServiceException;

    /**
     * 查询组织成员
     *
     * @param query 查询参数
     * @return QueryResult<OrganizationPositionDTO> 带分页参数的组织成员列表，可能返回空列表
     */
    QueryResult<OrganizationMemberDTO> listOrganizationMember(@NotNull OrganizationMemberQuery query);

    /**
     * 查询组织成员邀请
     *
     * @permission 只能查询组织自己的记录，也就是必须设置 organizationId
     *
     * @param query 查询参数
     * @return QueryResult<OrganizationMemberInvitationDTO> 带分页参数的组织成员邀请列表，可能返回空列表
     */
    QueryResult<OrganizationMemberInvitationDTO> listOrganizationMemberInvitation(
            @NotNull OrganizationMemberInvitationQuery query);

    /**
     * 更新组织成员
     *
     * @param request UpdateOrganizationMemberRequest
     * @return OrganizationMemberDTO
     */
    OrganizationMemberDTO updateOrganizationMember(UpdateOrganizationMemberRequest request) throws ServiceException;

    /**
     * 清除组织成员的组织职位，该接口不对外开放
     * 即通过组织职位编号，是该组织职位编号的成员，设置组织职位为0
     *
     * @private 内部方法
     *
     * @param organizationPositionId 组织职位编号
     * @return 被清除职位的组织成员数量
     */
    Integer clearOrganizationPositions(@NotNull @Positive Long organizationPositionId);

    /**
     * 拒绝加入组织邀请
     *
     * @permission 需要检查 organizationMemberInvitationId 是否属于该用户
     *
     * @param id 组织成员邀请编号
     * @return 拒绝结果
     */
    OrganizationMemberInvitationDTO rejectInvitation(@NotNull @Positive Long id);

    /**
     * 更新组织成员邀请的状态为 EXPIRED
     *
     * @private 内部方法
     *
     * @see com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum -> EXPIRED
     *
     * @param id 组织成员邀请编号
     * @return 更新结果
     */
    OrganizationMemberInvitationDTO updateInvitationStatusToExpired(@NotNull @Positive Long id);

}
