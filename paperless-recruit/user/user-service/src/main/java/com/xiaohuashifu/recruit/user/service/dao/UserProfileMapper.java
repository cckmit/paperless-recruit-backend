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

    int insertUserProfile(Long userId);

    UserProfileDO getUserProfile(Long id);

    List<UserProfileDO> listUserProfiles(UserProfileQuery query);

    UserProfileDO getUserProfileByUserId(Long userId);

    int countByUserId(Long userId);

    int updateFullName(@Param("userId") Long userId, @Param("fullName") String fullName);

    int updateStudentNumber(@Param("userId") Long userId, @Param("studentNumber") String studentNumber);

    int updateCollegeAndMajor(@Param("userId") Long userId, @Param("college") String college,
                              @Param("major") String major);

    int updateIntroduction(@Param("userId") Long userId, @Param("introduction") String introduction);


}
