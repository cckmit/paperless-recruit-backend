package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationLabelConstants;
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

    /**
     * 创建组织，需要没有使用过的邮箱，用于注册组织的主体账号
     *
     * @errorCode InvalidParameter: 邮箱或验证码或密码格式错误
     *              OperationConflict: 邮箱已经存在
     *              OperationConflict.Lock: 无法获取关于该邮箱的锁
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *              UnknownError: 注册主体失败
     *
     * @param email 组织主体的邮箱
     * @param authCode 邮箱验证码
     * @param password 密码
     * @return OrganizationDTO 组织对象
     */
    Result<OrganizationDTO> createOrganization(
            @NotBlank(message = "The email can't be blank.") @Email(message = "The email format error.") String email,
            @NotBlank(message = "The authCode can't be blank.") @AuthCode String authCode,
            @NotEmpty(message = "The password can't be empty.") @Password String password);

    /**
     * 添加组织的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 组织标签数量超过规定数量
     *
     * @param organizationId 组织编号
     * @param labelName 标签名
     * @return 添加后的组织对象
     */
    Result<OrganizationDTO> addLabel(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 删除组织的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 该标签不存在
     *
     * @param organizationId 组织编号
     * @param labelName 标签名
     * @return 删除标签后的组织
     */
    Result<OrganizationDTO> removeLabel(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 获取组织
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotFound: 该编号的组织不存在
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

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱或标题格式错误
     *              OperationConflict: 该邮箱已经被注册，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    Result<Void> sendEmailAuthCodeForSignUp(@NotBlank(message = "The email can't be blank.")
                                            @Email(message = "The email format error.") String email);

}
