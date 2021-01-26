package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.UserVO;

/**
 * 描述：用户管理器
 *
 * @author xhsf
 * @create 2021/1/26 12:46
 */
public interface UserManager {

    /**
     * 获取用户
     *
     * @param userId 用户编号
     * @return UserVO
     */
    UserVO getUser(Long userId);

}
