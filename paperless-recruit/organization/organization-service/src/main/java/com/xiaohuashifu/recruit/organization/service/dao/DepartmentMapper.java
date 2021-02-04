package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：部门数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    int addLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabels(String label);

    int increaseNumberOfMembers(Long id);

    int decreaseNumberOfMembers(Long id);

}
