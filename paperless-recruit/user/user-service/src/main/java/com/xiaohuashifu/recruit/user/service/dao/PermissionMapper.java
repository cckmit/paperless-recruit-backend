package com.xiaohuashifu.recruit.user.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PermissionMapper extends BaseMapper<PermissionDO> {

    default PermissionDO selectByPermissionName(String permissionName) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getPermissionName, permissionName);
        return selectOne(wrapper);
    }

    Set<String> selectAvailablePermissionNamesByUserId(Long userId);

}
