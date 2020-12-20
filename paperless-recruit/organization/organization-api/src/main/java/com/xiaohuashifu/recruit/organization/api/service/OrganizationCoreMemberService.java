package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;

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
     * @permission 必须验证 organizationId 和 organizationMemberId 是属于用户本身的
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return OrganizationCoreMemberDTO
     */
    Result<OrganizationCoreMemberDTO> saveOrganizationCoreMember(Long organizationId, Long organizationMemberId);

    /**
     * 删除组织核心成员
     *
     * @permission 必须验证 organizationId 和 organizationMemberId 是属于用户本身的
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return OrganizationCoreMemberDTO
     */
    Result<Void> deleteOrganizationCoreMember(Long organizationId, Long organizationMemberId);

    /**
     * 获取组织的所有核心成员
     *
     * @param organizationId 组织编号
     * @return OrganizationCoreMemberDTO 可能返回空列表，如果该组织没有核心成员
     */
    Result<List<OrganizationCoreMemberDTO>> listOrganizationCoreMembersByOrganizationId(Long organizationId);

}
