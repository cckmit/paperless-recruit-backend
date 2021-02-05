package com.xiaohuashifu.recruit.user.service.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.user.service.do0.CollegeDO;

/**
 * 描述：学院表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface CollegeMapper extends BaseMapper<CollegeDO> {

    default CollegeDO selectByCollegeName(String collegeName) {
        LambdaUpdateWrapper<CollegeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CollegeDO::getCollegeName, collegeName);
        return selectOne(wrapper);
    }

}
