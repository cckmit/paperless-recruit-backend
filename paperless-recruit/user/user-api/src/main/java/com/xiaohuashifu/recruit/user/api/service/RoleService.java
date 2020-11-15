package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：角色服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public interface RoleService {

    @interface SaveRole{}
    /**
     * 创建角色
     *
     * @param roleDTO 需要parentRoleId，roleName，description和available
     * @return Result<RoleDTO>
     */
    default Result<RoleDTO> saveRole(@NotNull RoleDTO roleDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除角色
     *
     * @param id 角色编号
     * @return Result<Void>
     */
    default Result<Void> deleteRole(@NotNull @Id Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取角色
     *
     * @param query 查询参数
     * @return 角色列表
     */
    default Result<List<RoleDTO>> getRole(@NotNull RoleQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    default Result<List<RoleDTO>> getRoleListByUserId(@NotNull @Id Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取角色
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    default Result<RoleDTO> getRole(@NotNull @Id Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新角色名
     *
     * @param id 角色编号
     * @param roleName 角色名
     * @return Result<RoleDTO> 更新后的角色对象
     */
    default Result<RoleDTO> updateRoleName(@NotNull @Id Long id, @NotBlank @Size(min = 1, max = 64) String roleName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新角色描述
     *
     * @param id 角色编号
     * @param description 角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    default Result<RoleDTO> updateDescription(@NotNull @Id Long id,
                                              @NotBlank @Size(min = 1, max = 200) String description) {
        throw new UnsupportedOperationException();
    }

    /**
     * 禁用角色
     *
     * @param id 角色编号
     * @return Result<RoleDTO> 禁用后的角色对象
     */
    default Result<RoleDTO> disableRole(@NotNull @Id Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 解禁角色
     *
     * @param id 角色编号
     * @return Result<RoleDTO> 解禁后的角色对象
     */
    default Result<RoleDTO> enableRole(@NotNull @Id Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置父角色
     *
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return Result<RoleDTO> 设置父角色后的角色对象
     */
    default Result<RoleDTO> enableRole(@NotNull @Id Long id, @NotNull @Id Long parentRoleId) {
        throw new UnsupportedOperationException();
    }

}
