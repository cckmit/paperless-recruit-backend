package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.RoleConstants;
import com.xiaohuashifu.recruit.user.api.dto.DisableRoleDTO;
import com.xiaohuashifu.recruit.user.api.dto.EnableRoleDTO;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.request.CreateRoleRequest;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;

import javax.validation.constraints.*;

/**
 * 描述：角色服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public interface RoleService {

    /**
     * 创建角色
     * 角色名必须不存在
     * 如果父角色被禁用了，则该角色也会被禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 父角色不存在
     *              OperationConflict: 角色名已经存在
     *
     * @param saveRolePO 保存 Role 需要的参数对象
     * @return Result<RoleDTO>
     */
    Result<RoleDTO> saveRole(@NotNull(message = "The saveRolePO can't be null.") CreateRoleRequest saveRolePO);

    /**
     * 创建用户角色，也就是给用户绑定角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 角色不存在
     *              OperationConflict: 用户已经有该角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Result<Void>
     */
    Result<Void> saveUserRole(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId,
            @NotNull(message = "The roleId can't be null.")
            @Positive(message = "The roleId must be greater than 0.") Long roleId);

    /**
     * 创建角色权限，也就是给角色绑定权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 权限不存在
     *              OperationConflict: 该角色已经有该权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return Result<Void>
     */
    Result<Void> saveRolePermission(
            @NotNull(message = "The roleId can't be null.")
            @Positive(message = "The roleId must be greater than 0.") Long roleId,
            @NotNull(message = "The permissionId can't be null.")
            @Positive(message = "The permissionId must be greater than 0.") Long permissionId);

    /**
     * 删除角色，只允许没有子角色的角色删除
     * 同时会删除此角色拥有的所有权限（Permission）的关联关系
     * 和拥有此角色的用户之间的关联关系
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 该角色存在子角色
     *
     * @param id 角色编号
     * @return Result<Void>
     */
    Result<Void> removeRole(@NotNull(message = "The id can't be null.")
                            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 删除用户绑定的角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 角色不存在 | 用户不存在该角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Result<Void>
     */
    Result<Void> removeUserRole(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId,
            @NotNull(message = "The roleId can't be null.")
            @Positive(message = "The roleId must be greater than 0.") Long roleId);

    /**
     * 删除角色绑定的权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 权限不存在 | 角色不存在该权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return Result<Void>
     */
    Result<Void> removeRolePermission(
            @NotNull(message = "The roleId can't be null.")
            @Positive(message = "The roleId must be greater than 0.") Long roleId,
            @NotNull(message = "The permissionId can't be null.")
            @Positive(message = "The permissionId must be greater than 0.") Long permissionId);

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的角色不存在
     *
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    Result<RoleDTO> getRole(@NotNull(message = "The id can't be null.")
                            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<RoleDTO>> 带分页信息的角色列表，可能返回空列表
     */
    Result<PageInfo<RoleDTO>> listRoles(@NotNull(message = "The query can't be null.") RoleQuery query);

    /**
     * 更新角色名，新角色名必须不存在
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 新角色名已经存在
     *
     * @param id 角色编号
     * @param newRoleName 新角色名
     * @return Result<RoleDTO> 更新后的角色对象
     */
    Result<RoleDTO> updateRoleName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newRoleName can't be blank.")
            @Size(max = RoleConstants.MAX_ROLE_NAME_LENGTH,
                    message = "The length of roleName must not be greater than "
                            + RoleConstants.MAX_ROLE_NAME_LENGTH + ".")
                    String newRoleName);

    /**
     * 更新角色描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *
     * @param id 角色编号
     * @param newDescription 新角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    Result<RoleDTO> updateDescription(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newDescription can't be blank.")
            @Size(max = RoleConstants.MAX_DESCRIPTION_LENGTH,
                    message = "The length of description must not be greater than "
                            + RoleConstants.MAX_DESCRIPTION_LENGTH + ".")
                    String newDescription);

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经被禁用
     *
     * @param id 角色编号
     * @return Result<DisableRoleDTO> 禁用的数量和禁用后的角色对象
     */
    Result<DisableRoleDTO> disableRole(@NotNull(message = "The id can't be null.")
                                            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁角色（且子角色可用状态也被解禁，递归解禁）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经可用 | 父角色被禁用，无法解禁该角色
     *
     * @param id 角色编号
     * @return Result<EnableRoleDTO> 解禁的数量和解禁后的角色对象
     */
    Result<EnableRoleDTO> enableRole(@NotNull(message = "The id can't be null.")
                                           @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 设置父角色
     * 设置parentRoleId为0表示取消父角色设置
     * 如果父亲角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 父角色不存在
     *              OperationConflict: 新旧父角色不能相同
     *
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return Result<DisableRoleDTO> 禁用的数量和禁用后的角色对象
     */
    Result<DisableRoleDTO> setParentRole(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The parentRoleId can't be null.")
            @PositiveOrZero(message = "The parentRoleId must be greater than or equal to 0.") Long parentRoleId);

}
