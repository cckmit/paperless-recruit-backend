package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

/**
 * 描述：权限服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public interface PermissionService {

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @param roleIdList 角色id列表
     * @return 角色的权限列表
     */
    default Result<List<PermissionDTO>> getPermissionByRoleIdList(@NotEmpty List<Long> roleIdList) {
        throw new UnsupportedOperationException();
    }


    /**
     * 通过用户id获取用户权限列表
     *
     * @param userId 用户id
     * @return 用户的权限列表
     */
    default Result<List<PermissionDTO>> getPermissionByUserId(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询权限
     *
     * @return 权限列表
     */
    default Result<List<PermissionDTO>> getPermission(@NotNull PermissionQuery query) {
        throw new UnsupportedOperationException();
    }

    @interface SavePermission{}
    /**
     * 创建权限
     *
     * @param permissionDTO parentPermissionId，permissionName，authorizationUrl，description和available
     * @return Result<PermissionDTO>
     */
    default Result<PermissionDTO> savePermission(@NotNull PermissionDTO permissionDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除角色，只允许没有子角色的角色删除
     * 同时会删除该角色所关联的所有权限（Permission）
     *
     * @param id 角色编号
     * @return Result<Void>
     */
    default Result<Void> deleteRole(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取角色
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    default Result<RoleDTO> getRole(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取角色
     *
     * @param query 查询参数
     * @return 角色列表
     */
    default Result<PageInfo<RoleDTO>> getRole(@NotNull RoleQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    default Result<List<RoleDTO>> getRoleListByUserId(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新角色名，新角色名必须不存在
     *
     * @param id 角色编号
     * @param newRoleName 新角色名
     * @return Result<RoleDTO> 更新后的角色对象
     */
    default Result<RoleDTO> updateRoleName(@NotNull @Positive Long id,
                                           @NotBlank @Size(min = 1, max = 64) String newRoleName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新角色描述
     *
     * @param id 角色编号
     * @param newDescription 新角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    default Result<RoleDTO> updateDescription(@NotNull @Positive Long id,
                                              @NotBlank @Size(min = 1, max = 200) String newDescription) {
        throw new UnsupportedOperationException();
    }

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的key为totalDisableCount和newRole
     */
    default Result<Map<String, Object>> disableRole(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 解禁角色（且子角色可用状态也被解禁，递归解禁）
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的角色对象，分别对应的key为totalEnableCount和newRole
     */
    default Result<Map<String, Object>> enableRole(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置父角色
     * 设置parentRoleId为0表示取消父角色设置
     * 如果父亲角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
     *
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return Result<Map<String, Object>> 禁用的数量和设置父角色后的角色对象，分别对应的key为totalDisableCount和newRole
     *         这里的禁用是因为如果父角色为禁用，则该角色必须也递归的禁用
     */
    default Result<Map<String, Object>> setParentRole(@NotNull @Positive Long id,
                                                      @NotNull @Positive Long parentRoleId) {
        throw new UnsupportedOperationException();
    }
}
