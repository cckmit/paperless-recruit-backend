package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.UpdateOrganizationLogoPO;
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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotAvailable: 标签不可用
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 组织标签数量超过规定数量
     *              OperationConflict.Lock: 获取组织标签的锁失败
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 添加后的组织对象
     */
    Result<OrganizationDTO> addLabel(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The label can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of label must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String label);

    /**
     * 删除组织的标签
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 删除标签后的组织
     */
    Result<OrganizationDTO> removeLabel(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The label can't be blank.")
            @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of label must not be greater than "
                            + OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String label);

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
     * 获取组织
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 用户编号格式错误
     *              InvalidParameter.NotFound: 该用户编号的组织不存在
     *
     * @param userId 用户编号
     * @return OrganizationDTO
     */
    Result<OrganizationDTO> getOrganizationByUserId(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId);

    /**
     * 查询组织
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationDTO> 查询结果，可能返回空列表
     */
    Result<PageInfo<OrganizationDTO>> listOrganizations(
            @NotNull(message = "The query can't be null.") OrganizationQuery query);

    /**
     * 更新组织名
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织名格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 新组织名已经存在
     *              OperationConflict.Lock: 获取组织名的锁失败
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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织介绍格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 更新参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              InternalError: 上传文件失败
     *              OperationConflict.Lock: 获取组织 logo 的锁失败
     *
     * @param updateOrganizationLogoPO 更新 logo 的参数对象
     * @return 更新后的组织
     */
    Result<OrganizationDTO> updateLogo(@NotNull(message = "The updateOrganizationLogoPO can't be null.")
                                               UpdateOrganizationLogoPO updateOrganizationLogoPO);

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @permission 必须是管理员
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 组织已经被禁用
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    Result<OrganizationDTO> disableOrganization(@NotNull(message = "The id can't be null.")
                                                @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁组织
     *
     * @permission 必须是管理员
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 组织已经可用
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    Result<OrganizationDTO> enableOrganization(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱或标题格式错误
     *              OperationConflict: 该邮箱已经被注册，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *              TooManyRequests: 请求太频繁
     *
     * @param email 邮箱
     * @return 发送结果
     */
    Result<Void> sendEmailAuthCodeForSignUp(@NotBlank(message = "The email can't be blank.")
                                            @Email(message = "The email format error.") String email);

    /**
     * 通过组织编号获取用户编号
     * 也就是查询组织的主体账号
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 用户编号，可能返回 null，若组织不存在
     */
    Long getUserId(Long id);

    /**
     * 验证组织的主体
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);

    /**
     * 通过组织编号判断组织是否存在
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 组织是否存在
     */
    boolean organizationExists(Long id);

    /**
     * 增加成员数，+1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 增加成员数后的组织对象
     */
    Result<OrganizationDTO> increaseMemberNumber(@NotNull(message = "The id can't be null.")
                                                 @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 减少成员数后的组织对象
     */
    Result<OrganizationDTO> decreaseMemberNumber(@NotNull(message = "The id can't be null.")
                                                 @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 检查组织是否存在，
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 检查结果
     */
    <T> Result<T> checkOrganizationStatus(Long id);

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
