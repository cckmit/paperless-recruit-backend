package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：组织服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface OrganizationService {

    /**
     * 创建组织，需要没有使用过的邮箱，用于注册组织的主体账号
     *
     * @param request CreateOrganizationRequest
     * @return OrganizationDTO 组织对象
     */
    OrganizationDTO createOrganization(@NotNull CreateOrganizationRequest request) throws ServiceException;

    /**
     * 获取组织
     *
     * @param id 组织编号
     * @return OrganizationDTO
     */
    OrganizationDTO getOrganization(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取组织
     *
     * @param userId 用户编号
     * @return OrganizationDTO
     */
    OrganizationDTO getOrganizationByUserId(@NotNull @Positive Long userId) throws NotFoundServiceException;

    /**
     * 查询组织
     *
     * @param query 查询参数
     * @return QueryResult<OrganizationDTO> 查询结果，可能返回空列表
     */
    QueryResult<OrganizationDTO> listOrganizations(@NotNull OrganizationQuery query);

    /**
     * 更新组织
     *
     * @param request UpdateOrganizationRequest
     * @return 更新后的组织
     */
    OrganizationDTO updateOrganization(@NotNull UpdateOrganizationRequest request) throws ServiceException;

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailAuthCodeForCreateOrganization(@NotBlank @Email String email);

}
