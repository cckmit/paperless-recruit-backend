package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：组织数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationMapper extends BaseMapper<OrganizationDO> {

    default OrganizationDO selectByUserId(Long userId) {
        LambdaQueryWrapper<OrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationDO::getUserId, userId);
        return selectOne(wrapper);
    }

    int addLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabels(String label);

    int increaseMemberNumber(Long id);

    int decreaseMemberNumber(Long id);

    int increaseNumberOfDepartments(Long id);

    int decreaseNumberOfDepartments(Long id);

}
