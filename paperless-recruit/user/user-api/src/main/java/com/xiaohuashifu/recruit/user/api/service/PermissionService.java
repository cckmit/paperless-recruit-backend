package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.PermissionConstants;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.po.SavePermissionPO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述：权限服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public interface PermissionService {

    /**
     * 创建权限
     * 权限名必须不存在
     * 如果父权限被禁用了，则该权限也会被禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 父权限不存在
     *              OperationConflict: 权限名已经存在
     *
     * @param savePermissionPO 保存 Permission 需要的参数
     * @return Result<PermissionDTO>
     */
    Result<PermissionDTO> savePermission(@NotNull(message = "The savePermissionPO can't be null.")
                                                 SavePermissionPO savePermissionPO);

    /**
     * 删除权限，只允许没有子权限的权限删除
     * 同时会删除与此权限关联的所有角色 Role 的关联关系
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在 | 存在子权限 |
     *
     * @param id 权限编号
     * @return Result<Void>
     */
    Result<Void> removePermission(@NotNull(message = "The id can't be null.")
                                  @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号对应的权限
     *
     * @param id 权限编号
     * @return Result<PermissionDTO>
     */
    Result<PermissionDTO> getPermission(@NotNull(message = "The id can't be null.")
                                        @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<PermissionDTO>> 带分页信息的权限列表，可能返回空列表
     */
    Result<PageInfo<PermissionDTO>> listPermissions(@NotNull(message = "The query can't be null.")
                                                            PermissionQuery query);

    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    Result<List<PermissionDTO>> listAllPermissions();

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param roleIds 角色id列表
     * @return 角色的权限列表，可能返回空列表
     */
    Result<List<PermissionDTO>> listPermissionsByRoleIds(@NotEmpty(message = "The roleIds can't be empty.")
                                                                 List<Long> roleIds);

    /**
     * 通过用户id获取用户权限列表
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @return 用户的权限列表，可能返回空列表
     */
    Result<List<PermissionDTO>> listPermissionsByUserId(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId);

    /**
     * 通过用户 id 获取用户权限 Authority 列表
     * 该权限代表的是权限字符串，而不是 Permission 对象
     * 主要用于Spring Security框架鉴权使用
     * 包含角色和权限
     * 角色的转换格式为：ROLE_{role_name}
     * 权限的转换格式为：{permission_name}
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @return 用户的权限 Authority 列表，可能返回空列表
     */
    Result<Set<String>> listAuthoritiesByUserId(@NotNull(message = "The userId can't be null.")
                                                @Positive(message = "The userId must be greater than 0.") Long userId);

    /**
     * 更新权限名，新权限名必须不存在
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 新权限名已经存在
     *
     * @param id 权限编号
     * @param newPermissionName 新权限名
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    Result<PermissionDTO> updatePermissionName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPermissionName can't be blank.")
            @Size(max = PermissionConstants.MAX_PERMISSION_NAME_LENGTH,
                    message = "The length of permissionName must not be greater than "
                            + PermissionConstants.MAX_PERMISSION_NAME_LENGTH + ".")
                    String newPermissionName);

    /**
     * 更新授权路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newAuthorizationUrl 新授权路径
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    Result<PermissionDTO> updateAuthorizationUrl(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newAuthorizationUrl can't be blank.")
            @Size(max = PermissionConstants.MAX_AUTHORIZATION_URL_LENGTH,
                    message = "The length of authorizationUrl must not be greater than"
                            + PermissionConstants.MAX_AUTHORIZATION_URL_LENGTH + ".")
                    String newAuthorizationUrl);

    /**
     * 更新权限描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newDescription 新权限描述
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    Result<PermissionDTO> updateDescription(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newDescription can't be blank.")
            @Size(max = PermissionConstants.MAX_DESCRIPTION_LENGTH,
                    message = "The length of description must not be greater than "
                            + PermissionConstants.MAX_DESCRIPTION_LENGTH + ".")
                    String newDescription);

    /**
     * 禁用权限（且子权限可用状态也被禁用，递归禁用）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 该权限已经被禁用
     *
     * @param id 权限编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的权限对象，
     *          分别对应的 key 为 totalDisableCount 和 newPermission
     */
    Result<Map<String, Object>> disablePermission(@NotNull(message = "The id can't be null.")
                                                  @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁权限（且子权限可用状态也被解禁，递归解禁）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 该权限已经可用 | 父权限被禁用，无法解禁该权限
     *
     * @param id 权限编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的权限对象
     *          分别对应的 key 为 totalEnableCount 和 newPermission
     */
    Result<Map<String, Object>> enablePermission(@NotNull(message = "The id can't be null.")
                                                 @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 设置父权限
     * 设置 parentPermissionId 为0表示取消父权限设置
     * 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 权限不存在 | 父权限不存在
     *              OperationConflict: 新旧父权限相同
     *
     * @param id 权限编号
     * @param parentPermissionId 父权限编号
     * @return Result<Map<String, Object>>
     *          禁用的数量和设置父权限后的权限对象，分别对应的key为totalDisableCount和newPermission
     *          这里的禁用是因为如果父权限为禁用，则该权限必须也递归的禁用
     */
    Result<Map<String, Object>> setParentPermission(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The parentPermissionId can't be null.")
            @PositiveOrZero(message = "The parentPermissionId must be greater than or equal to 0.")
                    Long parentPermissionId);
}
