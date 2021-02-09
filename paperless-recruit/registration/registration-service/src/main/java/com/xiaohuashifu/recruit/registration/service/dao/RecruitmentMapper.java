package com.xiaohuashifu.recruit.registration.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 描述：招新数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface RecruitmentMapper extends BaseMapper<RecruitmentDO> {

    int addRecruitmentCollege(@Param("id") Long id, @Param("collegeId") Long collegeId);

    int addRecruitmentMajor(@Param("id") Long id, @Param("majorId") Long majorId);

    int addRecruitmentGrade(@Param("id") Long id, @Param("grade") String grade);

    int addRecruitmentDepartment(@Param("id") Long id, @Param("departmentId") Long departmentId);

    int removeRecruitmentCollege(@Param("id") Long id, @Param("collegeId") Long collegeId);

    int removeRecruitmentMajor(@Param("id") Long id, @Param("majorId") Long majorId);

    int removeRecruitmentGrade(@Param("id") Long id, @Param("grade") String grade);

    int removeRecruitmentDepartment(@Param("id") Long id, @Param("departmentId") Long departmentId);

    int updateRecruitmentStatusWhenReleaseTimeLessThan(@Param("time") LocalDateTime time,
                                                       @Param("oldRecruitmentStatus") String oldRecruitmentStatus,
                                                       @Param("newRecruitmentStatus") String newRecruitmentStatus);

    int updateRecruitmentStatusWhenRegistrationTimeFromLessThan(
            @Param("time") LocalDateTime time, @Param("oldRecruitmentStatus") String oldRecruitmentStatus,
            @Param("newRecruitmentStatus") String newRecruitmentStatus);

    int updateRecruitmentStatusWhenRegistrationTimeToLessThan(
            @Param("time") LocalDateTime time, @Param("oldRecruitmentStatus") String oldRecruitmentStatus,
            @Param("newRecruitmentStatus") String newRecruitmentStatus);

}

