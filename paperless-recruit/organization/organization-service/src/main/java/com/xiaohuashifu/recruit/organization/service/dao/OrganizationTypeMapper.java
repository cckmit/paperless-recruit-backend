package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationTypeDO;

/**
 * 描述：组织类型映射
 *
 * @author xhsf
 * @create 2021/3/9 14:17
 */
public interface OrganizationTypeMapper extends BaseMapper<OrganizationTypeDO> {

    default OrganizationTypeDO selectOrganizationTypeByTypeName(String typeName) {
        LambdaQueryWrapper<OrganizationTypeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationTypeDO::getTypeName, typeName);
        return selectOne(wrapper);
    }

}
