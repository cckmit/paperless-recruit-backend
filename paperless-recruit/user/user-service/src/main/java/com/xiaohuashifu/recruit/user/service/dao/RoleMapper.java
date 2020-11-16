package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:44
 */
public interface RoleMapper {

    int saveRole(RoleDO roleDO);

    int deleteRole(Long id);

    int deleteRolePermissionByRoleId(Long roleId);

    RoleDO getRole(Long id);

    List<RoleDO> getRoleByQuery(RoleQuery query);

    List<RoleDO> getRoleListByUserId(Long userId);

    /**
     * 通过父角色编号获取角色编号列表
     * 也就是获取该编号所有的子角色
     *
     * @param parentRoleId 父角色编号
     * @return 子角色编号列表
     */
    List<Long> getIdListByParentRoleId(Long parentRoleId);

    /**
     * 通过父角色编号和角色状态获取角色编号列表
     * 也就是获取该编号所有可用或不可用的子角色
     *
     * @param parentRoleId 父角色编号
     * @param available 角色状态
     * @return 子角色编号列表
     */
    List<Long> getIdListByParentRoleIdAndAvailable(@Param("parentRoleId") Long parentRoleId,
                                                   @Param("available") Boolean available);

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

    int updateRoleName(@Param("id") Long id, @Param("roleName") String roleName);

    int updateParentRoleId(@Param("id") Long id, @Param("parentRoleId") Long parentRoleId);

    int updateDescription(@Param("id") Long id, @Param("description") String description);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

    /**
     * 更新available字段为true如果available字段原来为false
     *
     * @param id 角色编号
     * @return 成功更新数量
     */
    int updateAvailableIfUnavailable(Long id);


}
