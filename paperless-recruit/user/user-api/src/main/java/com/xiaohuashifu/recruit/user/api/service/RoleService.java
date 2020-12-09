package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.po.SaveRolePO;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

/**
 * 描述：角色服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public interface RoleService {

    @interface SaveRole{}
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
    Result<RoleDTO> saveRole(@NotNull SaveRolePO saveRolePO);

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
    Result<Void> saveUserRole(@NotNull @Positive Long userId, @NotNull @Positive Long roleId);

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
    Result<Void> saveRolePermission(@NotNull @Positive Long roleId, @NotNull @Positive Long permissionId);

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
    Result<Void> removeRole(@NotNull @Positive Long id);

    /**
     * 删除用户绑定的角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 角色不存在 | 用户不存在该角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Result<Void>
     */
    Result<Void> removeUserRole(@NotNull @Positive Long userId, @NotNull @Positive Long roleId);

    /**
     * 删除角色绑定的权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 权限不存在 | 角色不存在该权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return Result<Void>
     */
    Result<Void> removeRolePermission(@NotNull @Positive Long roleId, @NotNull @Positive Long permissionId);

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的角色不存在
     *
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    Result<RoleDTO> getRole(@NotNull @Positive Long id);

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<RoleDTO>> 带分页信息的角色列表，可能返回空列表
     */
    Result<PageInfo<RoleDTO>> listRoles(@NotNull RoleQuery query);

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @return 用户的角色列表，可能返回空列表
     */
    Result<List<RoleDTO>> getRoleListByUserId(@NotNull @Positive Long userId);

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
    Result<RoleDTO> updateRoleName(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 64) String newRoleName);

    /**
     * 更新角色描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *
     * @param id 角色编号
     * @param newDescription 新角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    Result<RoleDTO> updateDescription(@NotNull @Positive Long id,
                                      @NotBlank @Size(min = 1, max = 200) String newDescription);

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经被禁用
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的 key 为 totalDisableCount 和 newRole
     */
    Result<Map<String, Object>> disableRole(@NotNull @Positive Long id);

    /**
     * 解禁角色（且子角色可用状态也被解禁，递归解禁）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经可用 | 父角色被禁用，无法解禁该角色
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的角色对象，分别对应的key为totalEnableCount和newRole
     */
    Result<Map<String, Object>> enableRole(@NotNull @Positive Long id);

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
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的key为totalDisableCount和newRole
     */
    Result<Map<String, Object>> setParentRole(@NotNull @Positive Long id, @NotNull @PositiveOrZero Long parentRoleId);

}
