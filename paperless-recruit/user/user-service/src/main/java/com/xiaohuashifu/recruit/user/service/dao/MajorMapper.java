package com.xiaohuashifu.recruit.user.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
import com.xiaohuashifu.recruit.user.service.do0.UserDO;

/**
 * 描述：专业表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface MajorMapper extends BaseMapper<MajorDO> {

    default MajorDO selectByMajorName(String majorName) {
        LambdaQueryWrapper<MajorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MajorDO::getMajorName, majorName);
        return selectOne(wrapper);
    }
}
