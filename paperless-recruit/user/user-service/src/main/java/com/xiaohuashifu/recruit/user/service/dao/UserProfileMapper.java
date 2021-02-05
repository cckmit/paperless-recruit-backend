package com.xiaohuashifu.recruit.user.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.user.service.do0.UserProfileDO;

/**
 * 描述：用户信息表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface UserProfileMapper extends BaseMapper<UserProfileDO> {

    default UserProfileDO selectByUserId(Long userId) {
        LambdaQueryWrapper<UserProfileDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserProfileDO::getUserId, userId);
        return selectOne(wrapper);
    }

}
