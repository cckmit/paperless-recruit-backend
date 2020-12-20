package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 描述：组织核心成员服务
 *
 * @author xhsf
 * @create 2020/12/18 1:05
 */
public interface OrganizationCoreMemberService {

    /**
     * 保存组织核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Duplicate: 该组织核心成员已经存在
     *              OperationConflict.OverLimit: 该组织的核心成员数量已经超过限制
     *
     * @permission 必须验证 organizationId 和 organizationMemberId 是属于用户本身的
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return OrganizationCoreMemberDTO
     */
    Result<OrganizationCoreMemberDTO> saveOrganizationCoreMember(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId);

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 组织核心成员编号
     * @return 组织编号
     */
    Long getOrganizationId(Long id);

    /**
     * 删除组织核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @permission 必须验证该 id 是属于该组织的
     *
     * @param id 组织核心成员编号
     * @return OrganizationCoreMemberDTO
     */
    Result<Void> deleteOrganizationCoreMember(@NotNull(message = "The id can't be null.")
                                              @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取组织的所有核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param organizationId 组织编号
     * @return OrganizationCoreMemberDTO 可能返回空列表，如果该组织没有核心成员
     */
    Result<List<OrganizationCoreMemberDTO>> listOrganizationCoreMembersByOrganizationId(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId);

}
