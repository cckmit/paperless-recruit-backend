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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织成员不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict.Duplicate: 该组织核心成员已经存在
     *              OperationConflict.OverLimit: 该组织的核心成员数量已经超过限制
     *              Forbidden: 禁止操作
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
     * 删除组织核心成员
     *
     * @permission 该编号的组织成员所属组织必须是属于用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织核心成员不存在
     *              Forbidden.Unavailable: 组织不可用
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
