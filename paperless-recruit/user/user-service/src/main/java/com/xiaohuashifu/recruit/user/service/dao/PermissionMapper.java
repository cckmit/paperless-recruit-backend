package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.PermissionDO;
import com.xiaohuashifu.recruit.user.service.pojo.do0.RoleDO;

import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:44
 */
public interface PermissionMapper {
    List<PermissionDO> getPermissionListByRoleIdList(List<Long> roleIdList);

    List<PermissionDO> getPermissionByUserId(Long userId);

    List<PermissionDO> getPermissionByQuery(PermissionQuery query);
}
