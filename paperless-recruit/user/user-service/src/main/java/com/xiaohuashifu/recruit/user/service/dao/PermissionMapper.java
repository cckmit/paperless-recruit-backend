package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.PermissionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:44
 */
public interface PermissionMapper {
    int savePermission(PermissionDO permissionDO);

    int deletePermission(Long id);

    int deleteRolePermissionByPermissionId(Long permissionId);

    PermissionDO getPermission(Long id);

    List<PermissionDO> getPermissionListByRoleIdList(List<Long> roleIdList);

    List<PermissionDO> getPermissionByUserId(Long userId);

    List<PermissionDO> getPermissionByQuery(PermissionQuery query);

    List<PermissionDO> getAllPermission();

    int count(Long id);

    int countByPermissionName(String permissionName);

    int countByIdAndAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int countByParentPermissionId(Long parentPermissionId);


}
