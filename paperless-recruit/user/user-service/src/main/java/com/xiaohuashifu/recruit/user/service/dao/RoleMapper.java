package com.xiaohuashifu.recruit.user.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;

import java.util.Set;

/**
 * 描述：角色表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/11/12 20:44
 */
public interface RoleMapper extends BaseMapper<RoleDO> {

    default RoleDO selectByRoleName(String roleName) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getRoleName, roleName);
        return selectOne(wrapper);
    }

    Set<String> selectAvailableRoleNamesByUserId(Long userId);

}
