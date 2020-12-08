package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.CreateOrganizationPO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;

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
     * @param createOrganizationPO 创建组织的参数对象
     * @return OrganizationDTO
     */
    Result<OrganizationDTO> createOrganization(@NotNull CreateOrganizationPO createOrganizationPO);

    /**
     * 添加组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 添加结果
     */
    Result<Void> addLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName);

    /**
     * 删除组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 删除结果
     */
    Result<Void> removeLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName);

    /**
     * 获取组织
     *
     * @param id 组织编号
     * @return OrganizationDTO
     */
    Result<OrganizationDTO> getOrganization(@NotNull @Positive Long id);

    /**
     * 查询组织
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationDTO> 查询结果，可能返回空列表
     */
    Result<PageInfo<OrganizationDTO>> listOrganizations(@NotNull OrganizationQuery query);

    /**
     * 更新组织名
     *
     * @param id 组织编号
     * @param newOrganizationName 新组织名
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateOrganizationName(
            @NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 20) String newOrganizationName);

    /**
     * 更新组织名缩写
     *
     * @param id 组织编号
     * @param newAbbreviationOrganizationName 新组织名缩写
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateAbbreviationOrganizationName(
            @NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 5) String newAbbreviationOrganizationName);

    /**
     * 更新组织介绍
     *
     * @param id 组织编号
     * @param newIntroduction 新组织介绍
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateIntroduction(
            @NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 400) String newIntroduction);

    /**
     * 更新组织 Logo
     *
     * @param id 组织编号
     * @param newLogo 新 Logo
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateLogo(
            @NotNull @Positive Long id, @NotEmpty @Size(min = 1, max = 10240) byte[] newLogo);

    /**
     * 增加成员数，+1
     *
     * @param id 组织编号
     * @return 增加成员数结果，通过 Result.isSuccess() 判断
     */
    Result<Void> increaseMemberNumber(@NotNull @Positive Long id);

    /**
     * 减少成员数，-1
     *
     * @param id 组织编号
     * @return 减少成员数结果，通过 Result.isSuccess() 判断
     */
    Result<Void> decreaseMemberNumber(@NotNull @Positive Long id);

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    Result<OrganizationDTO> disableOrganization(@NotNull @Positive Long id);

    /**
     * 解禁组织
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    Result<OrganizationDTO> enableOrganization(@NotNull @Positive Long id);
}
