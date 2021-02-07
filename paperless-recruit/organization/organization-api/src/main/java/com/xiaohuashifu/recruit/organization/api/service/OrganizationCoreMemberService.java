package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
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
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return OrganizationCoreMemberDTO
     */
    OrganizationCoreMemberDTO createOrganizationCoreMember(
            @NotNull @Positive Long organizationId, @NotNull @Positive Long organizationMemberId)
            throws ServiceException;

    /**
     * 删除组织核心成员
     *
     * @permission 该编号的组织成员所属组织必须是属于用户主体本身
     *
     * @param id 组织核心成员编号
     */
    void removeOrganizationCoreMember(@NotNull @Positive Long id);

    /**
     * 获取核心成员
     *
     * @param id 组织核心成员编号
     * @return OrganizationCoreMemberDTO
     */
    OrganizationCoreMemberDTO getOrganizationCoreMember(Long id) throws NotFoundServiceException;

    /**
     * 获取组织的所有核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param organizationId 组织编号
     * @return OrganizationCoreMemberDTO 可能返回空列表，如果该组织没有核心成员
     */
    List<OrganizationCoreMemberDTO> listOrganizationCoreMembersByOrganizationId(@NotNull @Positive Long organizationId);

}
