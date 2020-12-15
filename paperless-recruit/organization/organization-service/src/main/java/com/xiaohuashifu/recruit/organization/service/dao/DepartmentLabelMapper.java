package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentLabelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：部门标签数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface DepartmentLabelMapper {

    int insertDepartmentLabel(DepartmentLabelDO departmentLabelDO);

    DepartmentLabelDO getDepartmentLabel(Long id);

    DepartmentLabelDO getDepartmentLabelByLabelName(String labelName);

    Boolean getAvailableByLabelName(String labelName);

    List<DepartmentLabelDO> listDepartmentLabels(DepartmentLabelQuery query);

    int countByLabelName(String labelName);

    int increaseReferenceNumber(Long id);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

}
