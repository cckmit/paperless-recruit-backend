package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentLabelDO;

/**
 * 描述：部门标签数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface DepartmentLabelMapper extends BaseMapper<DepartmentLabelDO> {

    default DepartmentLabelDO selectByLabelName(String labelName) {
        LambdaQueryWrapper<DepartmentLabelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepartmentLabelDO::getLabelName, labelName);
        return selectOne(wrapper);
    }

    int increaseReferenceNumber(Long id);

}
