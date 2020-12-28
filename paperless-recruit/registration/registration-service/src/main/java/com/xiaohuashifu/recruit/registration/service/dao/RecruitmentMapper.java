package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：招新数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface RecruitmentMapper {

    int insertRecruitment(RecruitmentDO recruitmentDO);

    RecruitmentDO getRecruitment(Long id);

    Boolean getAvailable(Long id);

    Long getOrganizationId(Long id);

    int count(Long id);

    int countRecruitmentCollegeIds(Long id);

    int countRecruitmentMajorIds(Long id);

    int addRecruitmentCollege(@Param("id") Long id, @Param("collegeId") Long collegeId);

    int addRecruitmentMajor(@Param("id") Long id, @Param("majorId") Long majorId);

    int addRecruitmentGrade(@Param("id") Long id, @Param("grade") String grade);

    int addRecruitmentDepartment(@Param("id") Long id, @Param("departmentId") Long departmentId);

    int clearRecruitmentCollegeIds(Long id);

    int clearRecruitmentMajorIds(Long id);

    int clearRecruitmentGrades(Long id);

    int clearRecruitmentDepartmentIds(Long id);

}

