package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：报名表数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface ApplicationFormMapper {

    int insertApplicationForm(ApplicationFormDO applicationFormDO);

    ApplicationFormDO getApplicationForm(Long id);

    Long getRecruitmentId(Long id);

    Long getUserId(Long id);

    String getAvatarUrl(Long id);

    String getAttachmentUrl(Long id);

    int countByUserIdAndRecruitmentId(@Param("userId") Long userId, @Param("recruitmentId") Long recruitmentId);

    int updateAttachmentUrl(@Param("id") Long id, @Param("attachmentUrl") String attachmentUrl);

    int updateFullName(@Param("id") Long id, @Param("fullName") String fullName);

    int updatePhone(@Param("id") Long id, @Param("phone") String phone);

    int updateEmail(@Param("id") Long id, @Param("email") String email);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

    int updateStudentNumber(@Param("id") Long id, @Param("studentNumber") String studentNumber);

    int updateNote(@Param("id") Long id, @Param("note") String note);

    int updateFirstDepartmentId(@Param("id") Long id, @Param("firstDepartmentId") Long firstDepartmentId);

    int updateSecondDepartmentId(@Param("id") Long id, @Param("secondDepartmentId") Long secondDepartmentId);

    int updateCollegeId(@Param("id") Long id, @Param("collegeId") Long collegeId);

    int updateMajor(@Param("id") Long id, @Param("majorId") Long majorId);
}

