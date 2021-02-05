package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;

import javax.validation.constraints.*;

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
     * 添加组织的标签
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 添加后的组织对象
     */
    OrganizationDTO addLabel(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String label)
            throws ServiceException;

    /**
     * 删除组织的标签
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 删除标签后的组织
     */
    OrganizationDTO removeLabel(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String label);

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
     * 更新组织名
     *
     * @param id 组织编号
     * @param organizationName 组织名
     * @return 更新后的组织
     */
    OrganizationDTO updateOrganizationName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = OrganizationConstants.MIN_ORGANIZATION_NAME_LENGTH,
                    max = OrganizationConstants.MAX_ORGANIZATION_NAME_LENGTH) String organizationName)
            throws ServiceException;

    /**
     * 更新组织名缩写
     *
     * @param id 组织编号
     * @param abbreviationOrganizationName 组织名缩写
     * @return 更新后的组织
     */
    OrganizationDTO updateAbbreviationOrganizationName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = OrganizationConstants.MIN_ABBREVIATION_ORGANIZATION_NAME_LENGTH,
                    max = OrganizationConstants.MAX_ABBREVIATION_ORGANIZATION_NAME_LENGTH)
                    String abbreviationOrganizationName) throws DuplicateServiceException;

    /**
     * 更新组织介绍
     *
     * @param id 组织编号
     * @param introduction 组织介绍
     * @return 更新后的组织
     */
    OrganizationDTO updateIntroduction(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = OrganizationConstants.MAX_ORGANIZATION_INTRODUCTION_LENGTH) String introduction);

    /**
     * 更新组织 Logo
     *
     * @param id 组织编号
     * @param logoUrl logoUrl
     * @return 更新后的组织
     */
    OrganizationDTO updateLogo(
            @NotNull @Positive Long id,
            @NotBlank @Pattern(regexp = "(organizations/logos/)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)") String logoUrl)
            throws ServiceException;

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    OrganizationDTO disableOrganization(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁组织
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    OrganizationDTO enableOrganization(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailAuthCodeForSignUp(@NotBlank @Email String email);

    /**
     * 增加成员数，+1
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 增加成员数后的组织对象
     */
    OrganizationDTO increaseNumberOfMembers(@NotNull @Positive Long id);

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 减少成员数后的组织对象
     */
    OrganizationDTO decreaseNumberOfMembers(@NotNull @Positive Long id);

    /**
     * 部门数加1
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 增加部门数后的组织对象
     */
    OrganizationDTO increaseNumberOfDepartments(@NotNull @Positive Long id);

    /**
     * 部门数减1
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 减少部门数后的组织对象
     */
    OrganizationDTO decreaseNumberOfDepartments(@NotNull @Positive Long id);

    /**
     * 删除组织的标签，通过标签名
     * 小心使用，一次性会删除所有的拥有该标签的组织的这个标签
     *
     * @private 内部方法
     *
     * @param label 标签名
     * @return 被删除标签的组织数量
     */
    int removeLabels(String label);
}
