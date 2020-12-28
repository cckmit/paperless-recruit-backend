package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

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

    int removeRecruitmentCollege(@Param("id") Long id, @Param("collegeId") Long collegeId);

    int removeRecruitmentMajor(@Param("id") Long id, @Param("majorId") Long majorId);

    int removeRecruitmentGrade(@Param("id") Long id, @Param("grade") String grade);

    int removeRecruitmentDepartment(@Param("id") Long id, @Param("departmentId") Long departmentId);

    int clearRecruitmentCollegeIds(Long id);

    int clearRecruitmentMajorIds(Long id);

    int clearRecruitmentGrades(Long id);

    int clearRecruitmentDepartmentIds(Long id);

    int updatePositionName(@Param("id") Long id, @Param("positionName") String positionName);

    int updateRecruitmentNumbers(@Param("id") Long id, @Param("recruitmentNumbers") String recruitmentNumbers);

    int updatePositionDuty(@Param("id") Long id, @Param("positionDuty") String positionDuty);

    int updatePositionRequirement(@Param("id") Long id, @Param("positionRequirement") String positionRequirement);

    int updateReleaseTime(@Param("id") Long id, @Param("releaseTime") LocalDateTime releaseTime);

    int updateRegistrationTimeFrom(@Param("id") Long id,
                                   @Param("registrationTimeFrom") LocalDateTime registrationTimeFrom);

    int updateRegistrationTimeFromToReleaseTime(Long id);

    int updateRegistrationTimeTo(@Param("id") Long id, @Param("registrationTimeTo") LocalDateTime registrationTimeTo);

    int updateRecruitmentStatus(@Param("id") Long id, @Param("oldRecruitmentStatus") String oldRecruitmentStatus,
                                @Param("newRecruitmentStatus") String newRecruitmentStatus);

    int updateRecruitmentStatusWhenNotEqual(@Param("id") Long id, @Param("recruitmentStatus") String recruitmentStatus);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);
}

