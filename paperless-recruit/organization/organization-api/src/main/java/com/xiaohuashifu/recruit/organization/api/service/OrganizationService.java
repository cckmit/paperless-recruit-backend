package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;

import javax.validation.constraints.*;

/**
 * 描述：组织服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface OrganizationService {

    @interface CreateOrganization {}
    /**
     * 创建组织，需要没有使用过的邮箱，用于注册组织的主体账号
     *
     * @param email 组织主体邮箱
     * @param authCode 邮箱验证码
     * @param organizationDTO 组织信息
     * @return OrganizationDTO
     */
    // TODO: 2020/12/7 这里的图片参数还未解决
    default Result<OrganizationDTO> createOrganization(
            @NotBlank @Email String email, @NotBlank @AuthCode String authCode,
            @NotNull OrganizationDTO organizationDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 添加组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 添加结果
     */
    default Result<Void> addLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除组织的标签
     *
     * @param id 组织编号
     * @param labelName 标签名
     * @return 删除结果
     */
    default Result<Void> removeLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取组织
     *
     * @param id 组织编号
     * @return OrganizationDTO
     */
    default Result<OrganizationDTO> getOrganization(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询组织
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationDTO> 查询结果，可能返回空列表
     */
    default Result<PageInfo<OrganizationDTO>> listOrganizations(@NotNull OrganizationQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新组织名
     *
     * @param id 组织编号
     * @param newOrganizationName 新组织名
     * @return 更新后的组织
     */
    default Result<OrganizationDTO> updateOrganizationName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 2, max = 20) String newOrganizationName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新组织名缩写
     *
     * @param id 组织编号
     * @param newAbbreviationOrganizationName 新组织名缩写
     * @return 更新后的组织
     */
    default Result<OrganizationDTO> updateAbbreviationOrganizationName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 2, max = 5) String newAbbreviationOrganizationName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新组织介绍
     *
     * @param id 组织编号
     * @param newIntroduction 新组织介绍
     * @return 更新后的组织
     */
    default Result<OrganizationDTO> updateIntroduction(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 1, max = 400) String newIntroduction) {
        throw new UnsupportedOperationException();
    }

//    /**
//     * 更新组织 Logo
//     *
//     * @param id 组织编号
//     * @param introduction 新组织介绍
//     * @return 更新后的组织
//     */
//    default Result<OrganizationDTO> updateLogo(
//            @NotNull @Positive Long id,
//            @NotBlank @Size(min = 1, max = 400) String introduction) {
//        throw new UnsupportedOperationException();
//    }

    /**
     * 增加成员数，+1
     *
     * @param id 组织编号
     * @return 增加成员数结果，通过 Result.isSuccess() 判断
     */
    default Result<Void> increaseMemberNumber(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 减少成员数，-1
     *
     * @param id 组织编号
     * @return 减少成员数结果，通过 Result.isSuccess() 判断
     */
    default Result<Void> decreaseMemberNumber(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    default Result<OrganizationDTO> disableOrganization(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 解禁组织
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    default Result<OrganizationDTO> enableOrganization(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }
}
