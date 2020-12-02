package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述：权限服务 RPC 接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public interface PermissionService {

    @interface SavePermission{}
    /**
     * 创建权限
     * 权限名必须不存在
     * 如果父权限被禁用了，则该权限也会被禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 父权限不存在
     *              OperationConflict: 权限名已经存在
     *
     * @param permissionDTO parentPermissionId，permissionName，authorizationUrl，description和available
     * @return Result<PermissionDTO>
     */
    default Result<PermissionDTO> savePermission(@NotNull PermissionDTO permissionDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除权限，只允许没有子权限的权限删除
     * 同时会删除与此权限关联的所有角色 Role 的关联关系
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在 | 存在子权限 |
     *
     * @param id 权限编号
     * @return Result<Void>
     */
    default Result<Void> removePermission(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号对应的权限
     *
     * @param id 权限编号
     * @return Result<PermissionDTO>
     */
    default Result<PermissionDTO> getPermission(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<PermissionDTO>> 带分页信息的权限列表，可能返回空列表
     */
    default Result<PageInfo<PermissionDTO>> listPermissions(@NotNull PermissionQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    default Result<List<PermissionDTO>> listAllPermissions() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param roleIds 角色id列表
     * @return 角色的权限列表，可能返回空列表
     */
    default Result<List<PermissionDTO>> listPermissionsByRoleIds(@NotEmpty List<Long> roleIds) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过用户id获取用户权限列表
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @return 用户的权限列表，可能返回空列表
     */
    default Result<List<PermissionDTO>> listPermissionsByUserId(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

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
    default Result<Set<String>> listAuthoritiesByUserId(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

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
    default Result<PermissionDTO> updatePermissionName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 1, max = 64) String newPermissionName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新授权路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newAuthorizationUrl 新授权路径
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    default Result<PermissionDTO> updateAuthorizationUrl(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 1, max = 255) String newAuthorizationUrl) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新权限描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newDescription 新权限描述
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    default Result<PermissionDTO> updateDescription(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 1, max = 200) String newDescription) {
        throw new UnsupportedOperationException();
    }

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
    default Result<Map<String, Object>> disablePermission(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

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
    default Result<Map<String, Object>> enablePermission(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

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
    default Result<Map<String, Object>> setParentPermission(
            @NotNull @Positive Long id,
            @NotNull @PositiveOrZero Long parentPermissionId) {
        throw new UnsupportedOperationException();
    }
}
