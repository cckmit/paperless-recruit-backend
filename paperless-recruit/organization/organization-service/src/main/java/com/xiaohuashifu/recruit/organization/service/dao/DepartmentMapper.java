package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDepartmentLabelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：部门数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface DepartmentMapper {

    int insertDepartment(DepartmentDO departmentDO);

    int insertLabel(DepartmentDepartmentLabelDO departmentDepartmentLabelDO);

    int deleteLabelsByLabelName(String labelName);

    int deleteLabelByDepartmentIdAndLabelName(@Param("departmentId") Long departmentId,
                                              @Param("labelName") String labelName);

    DepartmentDO getDepartment(Long id);

    Boolean getDeactivated(Long id);

    String getLogoUrlByDepartmentId(Long departmentId);

    Long getOrganizationIdByDepartmentId(Long departmentId);

    List<DepartmentDO> listDepartments(DepartmentQuery query);

    List<String> listDepartmentLabelNamesByDepartmentId(Long departmentId);

    int countByOrganizationIdAndDepartmentName(@Param("organizationId") Long organizationId,
                                               @Param("departmentName") String departmentName);

    int countByOrganizationIdAndAbbreviationDepartmentName(
            @Param("organizationId") Long organizationId,
            @Param("abbreviationDepartmentName") String abbreviationDepartmentName);

    int countLabelByDepartmentId(Long departmentId);

    int countLabelByDepartmentIdAndLabelName(@Param("departmentId") Long departmentId,
                                             @Param("labelName") String labelName);

    int updateDepartmentName(@Param("id") Long id, @Param("departmentName") String departmentName);

    int updateAbbreviationDepartmentName(@Param("id") Long id,
                                         @Param("abbreviationDepartmentName") String abbreviationDepartmentName);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

    int updateLogoUrl(@Param("id") Long id, @Param("logoUrl") String logoUrl);

    int updateDeactivated(@Param("id") Long id, @Param("deactivated") Boolean deactivated);

    int increaseMemberNumber(Long id);

    int decreaseMemberNumber(Long id);

}
