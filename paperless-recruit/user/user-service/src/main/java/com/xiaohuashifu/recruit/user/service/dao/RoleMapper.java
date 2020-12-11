package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：角色表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/11/12 20:44
 */
public interface RoleMapper {

    int insertRole(RoleDO roleDO);

    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int insertRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    int deleteRole(Long id);

    int deleteRolePermissionByRoleId(Long roleId);

    int deleteRolePermissionByRoleIdAndPermissionId(@Param("roleId") Long roleId,
                                                    @Param("permissionId") Long permissionId);

    int deleteUserRoleByRoleId(Long roleId);

    int deleteUserRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    RoleDO getRole(Long id);

    List<RoleDO> listRoles(RoleQuery query);

    List<RoleDO> listRolesByUserId(Long userId);

    List<RoleDO> listAllAvailableRoles();

    /**
     * 通过父角色编号获取角色编号列表
     * 也就是获取该编号所有的子角色
     *
     * @param parentRoleId 父角色编号
     * @return 子角色编号列表
     */
    List<Long> listIdsByParentRoleId(Long parentRoleId);

    /**
     * 通过父角色编号和角色状态获取角色编号列表
     * 也就是获取该编号所有可用或不可用的子角色
     *
     * @param parentRoleId 父角色编号
     * @param available 角色状态
     * @return 子角色编号列表
     */
    List<Long> listIdsByParentRoleIdAndAvailable(@Param("parentRoleId") Long parentRoleId,
                                                 @Param("available") Boolean available);

    List<String> listRoleNamesByUserId(Long userId);

    /**
     * 通过角色编号获取父角色编号
     * @param id 角色编号
     * @return 父角色编号
     */
    Long getParentRoleIdById(Long id);

    int count(Long id);

    /**
     * 统计父角色是该编号的角色数量
     *
     * @param parentRoleId 父角色编号
     * @return 数量
     */
    int countByParentRoleId(Long parentRoleId);

    /**
     * 统计是该角色名的角色数量
     *
     * @param roleName 角色名
     * @return 数量
     */
    int countByRoleName(String roleName);

    int countByIdAndAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int countUserRoleByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int countRolePermissionByRoleIdAndPermissionId(@Param("roleId") Long roleId,
                                                   @Param("permissionId") Long permissionId);

    int updateRoleName(@Param("id") Long id, @Param("roleName") String roleName);

    int updateParentRoleId(@Param("id") Long id, @Param("parentRoleId") Long parentRoleId);

    int updateDescription(@Param("id") Long id, @Param("description") String description);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

    /**
     * 更新 available 字段为 true 如果 available 字段原来为 false
     *
     * @param id 角色编号
     * @return 成功更新数量
     */
    int updateAvailableIfUnavailable(Long id);

}
