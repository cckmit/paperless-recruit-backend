package com.xiaohuashifu.recruit.organization.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：部门数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    int insertDepartment(DepartmentDO departmentDO);

    DepartmentDO getDepartment(Long id);

    Boolean getDeactivated(Long id);

    String getLogoUrl(Long id);

    Long getOrganizationId(Long id);

    List<DepartmentDO> listDepartments(DepartmentQuery query);

    int count(Long id);

    int countByOrganizationIdAndDepartmentName(@Param("organizationId") Long organizationId,
                                               @Param("departmentName") String departmentName);

    int countByOrganizationIdAndAbbreviationDepartmentName(
            @Param("organizationId") Long organizationId,
            @Param("abbreviationDepartmentName") String abbreviationDepartmentName);

    int countLabels(Long id);

    int updateDepartmentName(@Param("id") Long id, @Param("departmentName") String departmentName);

    int updateAbbreviationDepartmentName(@Param("id") Long id,
                                         @Param("abbreviationDepartmentName") String abbreviationDepartmentName);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

    int updateLogoUrl(@Param("id") Long id, @Param("logoUrl") String logoUrl);

    int updateDeactivated(@Param("id") Long id, @Param("deactivated") Boolean deactivated);

    int addLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabels(String label);

    int increaseNumberOfMembers(Long id);

    int decreaseNumberOfMembers(Long id);

}
