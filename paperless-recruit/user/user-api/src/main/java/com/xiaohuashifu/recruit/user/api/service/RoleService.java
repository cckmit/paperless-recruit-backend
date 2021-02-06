package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.constant.RoleConstants;
import com.xiaohuashifu.recruit.user.api.dto.*;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.api.request.CreateRoleRequest;

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
     * @param request CreateRoleRequest
     * @return RoleDTO
     */
    RoleDTO createRole(@NotNull CreateRoleRequest request) throws ServiceException;

    /**
     * 创建用户角色，也就是给用户绑定角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return UserRoleDTO
     */
    UserRoleDTO createUserRole(@NotNull @Positive Long userId, @NotNull @Positive Long roleId) throws ServiceException;

    /**
     * 创建角色权限，也就是给角色绑定权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return RolePermissionDTO
     */
    RolePermissionDTO createRolePermission(@NotNull @Positive Long roleId, @NotNull @Positive Long permissionId)
            throws ServiceException;

    /**
     * 删除角色，只允许没有子角色的角色删除
     * 同时会删除此角色拥有的所有权限（Permission）的关联关系
     * 和拥有此角色的用户之间的关联关系
     *
     * @param id 角色编号
     */
    void removeRole(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 删除用户绑定的角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     */
    void removeUserRole(@NotNull @Positive Long userId, @NotNull @Positive Long roleId);

    /**
     * 删除角色绑定的权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     */
    void removeRolePermission(@NotNull @Positive Long roleId, @NotNull @Positive Long permissionId);

    /**
     * 获取角色
     *
     * @param id 角色编号
     * @return RoleDTO
     */
    RoleDTO getRole(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取用户角色
     *
     * @param id 用户角色编号
     * @return UserRoleDTO
     */
    UserRoleDTO getUserRole(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取角色权限
     *
     * @param id 角色权限编号
     * @return RolePermissionDTO
     */
    RolePermissionDTO getRolePermission(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取角色
     *
     * @param query 查询参数
     * @return QueryResult<RoleDTO> 带分页信息的角色列表，可能返回空列表
     */
    QueryResult<RoleDTO> listRoles(@NotNull RoleQuery query);

    /**
     * 更新角色名，新角色名必须不存在
     *
     * @param id 角色编号
     * @param roleName 角色名
     * @return RoleDTO 更新后的角色对象
     */
    RoleDTO updateRoleName(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = RoleConstants.MAX_ROLE_NAME_LENGTH) String roleName) throws ServiceException;

    /**
     * 更新角色描述
     *
     * @param id 角色编号
     * @param description 角色描述
     * @return RoleDTO 更新后的角色对象
     */
    RoleDTO updateDescription(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = RoleConstants.MAX_DESCRIPTION_LENGTH) String description);

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @param id 角色编号
     * @return DisableRoleDTO 禁用的数量和禁用后的角色对象
     */
    DisableRoleDTO disableRole(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁角色（且子角色可用状态也被解禁，递归解禁）
     *
     * @param id 角色编号
     * @return EnableRoleDTO 解禁的数量和解禁后的角色对象
     */
    EnableRoleDTO enableRole(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 设置父角色
     * 设置parentRoleId为0表示取消父角色设置
     * 如果父亲角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
     *
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return DisableRoleDTO 禁用的数量和禁用后的角色对象
     */
    DisableRoleDTO setParentRole(@NotNull @Positive Long id, @NotNull @PositiveOrZero Long parentRoleId)
            throws ServiceException;

}
