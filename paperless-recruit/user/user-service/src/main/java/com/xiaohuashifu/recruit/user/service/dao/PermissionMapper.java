package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 描述：权限表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/11/12 20:44
 */
public interface PermissionMapper {

    int insertPermission(PermissionDO permissionDO);

    int deletePermission(Long id);

    int deleteRolePermissionByPermissionId(Long permissionId);

    PermissionDO getPermission(Long id);

    List<PermissionDO> listPermissionsByRoleIds(List<Long> roleIds);

    List<PermissionDO> listPermissionsByUserId(Long userId);

    List<PermissionDO> listPermissions(PermissionQuery query);

    List<PermissionDO> listAllAvailablePermissions();

    Set<String> listAvailablePermissionNamesByUserId(Long userId);

    List<Long> listIdsByParentPermissionId(Long parentPermissionId);

    List<Long> listIdsByParentPermissionIdAndAvailable(@Param("parentPermissionId") Long parentPermissionId,
                                                       @Param("available") Boolean available);

    int count(Long id);

    int countByPermissionName(String permissionName);

    int countByIdAndAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int countByParentPermissionId(Long parentPermissionId);

    int updatePermissionName(@Param("id") Long id, @Param("permissionName") String permissionName);

    int updateAuthorizationUrl(@Param("id") Long id, @Param("authorizationUrl") String authorizationUrl);

    int updateDescription(@Param("id") Long id, @Param("description") String description);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int updateAvailableIfUnavailable(Long id);

    int updateParentPermissionId(@Param("id") Long id, @Param("parentPermissionId") Long parentPermissionId);

}
