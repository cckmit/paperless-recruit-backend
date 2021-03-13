package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationCoreMemberRequest;

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
     * @param request CreateOrganizationCoreMemberRequest
     * @return OrganizationCoreMemberDTO
     */
    OrganizationCoreMemberDTO createOrganizationCoreMember(@NotNull CreateOrganizationCoreMemberRequest request)
            throws ServiceException;

    /**
     * 删除组织核心成员
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

    /**
     * 更新组织核心成员
     *
     * @param request UpdateOrganizationCoreMemberRequest
     * @return 更新后的组织核心成员
     */
    OrganizationCoreMemberDTO updateOrganizationCoreMember(@NotNull UpdateOrganizationCoreMemberRequest request);

}
