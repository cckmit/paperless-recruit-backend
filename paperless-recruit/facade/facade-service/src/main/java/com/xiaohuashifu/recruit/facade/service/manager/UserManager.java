package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.UserVO;
import com.xiaohuashifu.recruit.user.api.request.CreateUserBySmsAuthCodeRequest;

/**
 * 描述：用户管理器
 *
 * @author xhsf
 * @create 2021/1/26 12:46
 */
public interface UserManager {

    /**
     * 通过短信验证码注册用户
     *
     * @param request CreateUserBySmsAuthCodeRequest
     * @return UserVO
     */
    UserVO registerBySmsAuthCode(CreateUserBySmsAuthCodeRequest request);

    /**
     * 获取用户
     *
     * @param userId 用户编号
     * @return UserVO
     */
    UserVO getUser(Long userId);

}
