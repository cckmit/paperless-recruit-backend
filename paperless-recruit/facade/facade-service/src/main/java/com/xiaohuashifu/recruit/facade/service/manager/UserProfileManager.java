package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.UserProfileVO;

/**
 * 描述：用户信息管理器
 *
 * @author xhsf
 * @create 2021/1/9 12:46
 */
public interface UserProfileManager {

    /**
     * 获取用户信息
     *
     * @param userId 用户编号
     * @return UserProfileVO
     */
    UserProfileVO getUserProfile(Long userId);

}
