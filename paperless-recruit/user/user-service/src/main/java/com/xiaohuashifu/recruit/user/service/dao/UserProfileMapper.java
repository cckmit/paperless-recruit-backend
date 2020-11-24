package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.UserProfileDO;

import java.util.List;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface UserProfileMapper {

    int saveUserProfile(Long userId);


    UserProfileDO getUserProfile(Long id);

    List<UserProfileDO> getUserProfileByQuery(UserProfileQuery query);

    UserProfileDO getUserProfileByUserId(Long userId);

    int countByUserId(Long userId);

}
