package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;

/**
 * 描述：组织标签数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationLabelMapper extends BaseMapper<OrganizationLabelDO> {

    default OrganizationLabelDO selectByLabelName(String labelName) {
        LambdaQueryWrapper<OrganizationLabelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationLabelDO::getLabelName, labelName);
        return selectOne(wrapper);
    }

    int increaseReferenceNumber(Long id);
}
