package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.service.do0.UserProfileDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：用户信息表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface UserProfileMapper {

    int insertUserProfile(UserProfileDO userProfileDO);

    UserProfileDO getUserProfile(Long id);

    List<UserProfileDO> listUserProfiles(UserProfileQuery query);

    Long getUserId(Long id);

    int count(Long id);

    int countByUserId(Long userId);

    int updateFullName(@Param("id") Long id, @Param("fullName") String fullName);

    int updateStudentNumber(@Param("id") Long id, @Param("studentNumber") String studentNumber);

    int updateCollegeIdAndMajorId(@Param("id") Long id, @Param("collegeId") Long collegeId,
                                  @Param("majorId") Long majorId);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

}
