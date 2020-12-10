package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.CreateOrganizationPO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
     * @param createOrganizationPO 创建组织的参数对象
     * @return OrganizationDTO
     */
    Result<OrganizationDTO> createOrganization(@NotNull(message = "The createOrganizationPO can't be null.")
                                                       CreateOrganizationPO createOrganizationPO);

    /**
     * 添加组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 添加结果
     */
    Result<Void> addLabel(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 删除组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 删除结果
     */
    Result<Void> removeLabel(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 获取组织
     *
     * @param id 组织编号
     * @return OrganizationDTO
     */
    Result<OrganizationDTO> getOrganization(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 查询组织
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationDTO> 查询结果，可能返回空列表
     */
    Result<PageInfo<OrganizationDTO>> listOrganizations(
            @NotNull(message = "The query can't be null.") OrganizationQuery query);

    /**
     * 更新组织名
     *
     * @param id 组织编号
     * @param newOrganizationName 新组织名
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateOrganizationName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newOrganizationName can't be blank.")
            @Size(min = OrganizationConstants.MIN_ORGANIZATION_NAME_LENGTH,
                    max = OrganizationConstants.MAX_ORGANIZATION_NAME_LENGTH,
                    message = "The length of newOrganizationName must be between "
                            + OrganizationConstants.MIN_ORGANIZATION_NAME_LENGTH + " and "
                            + OrganizationConstants.MAX_ORGANIZATION_NAME_LENGTH + ".") String newOrganizationName);

    /**
     * 更新组织名缩写
     *
     * @param id 组织编号
     * @param newAbbreviationOrganizationName 新组织名缩写
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateAbbreviationOrganizationName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newAbbreviationOrganizationName can't be blank.")
            @Size(min = OrganizationConstants.MIN_ABBREVIATION_ORGANIZATION_NAME_LENGTH,
                    max = OrganizationConstants.MAX_ABBREVIATION_ORGANIZATION_NAME_LENGTH,
                    message = "The length of newAbbreviationOrganizationName must be between "
                            + OrganizationConstants.MIN_ABBREVIATION_ORGANIZATION_NAME_LENGTH + " and "
                            + OrganizationConstants.MAX_ABBREVIATION_ORGANIZATION_NAME_LENGTH + ".")
                    String newAbbreviationOrganizationName);

    /**
     * 更新组织介绍
     *
     * @param id 组织编号
     * @param newIntroduction 新组织介绍
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateIntroduction(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newIntroduction can't be blank.")
            @Size(max = OrganizationConstants.MAX_ORGANIZATION_INTRODUCTION_LENGTH,
                    message = "The length of newIntroduction must not be greater than "
                            + OrganizationConstants.MAX_ORGANIZATION_INTRODUCTION_LENGTH + ".") String newIntroduction);

    /**
     * 更新组织 Logo
     *
     * @param id 组织编号
     * @param newLogo 新 Logo
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateLogo(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The newLogo can't be null.")
            @Size(max = OrganizationConstants.MAX_ORGANIZATION_LOGO_LENGTH,
                    message = "The newLogo must not be greater than "
                            + OrganizationConstants.MAX_ORGANIZATION_LOGO_LENGTH + ".") byte[] newLogo);

    /**
     * 增加成员数，+1
     *
     * @param id 组织编号
     * @return 增加成员数结果，通过 Result.isSuccess() 判断
     */
    Result<Void> increaseMemberNumber(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 减少成员数，-1
     *
     * @param id 组织编号
     * @return 减少成员数结果，通过 Result.isSuccess() 判断
     */
    Result<Void> decreaseMemberNumber(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    Result<OrganizationDTO> disableOrganization(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁组织
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    Result<OrganizationDTO> enableOrganization(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

}
