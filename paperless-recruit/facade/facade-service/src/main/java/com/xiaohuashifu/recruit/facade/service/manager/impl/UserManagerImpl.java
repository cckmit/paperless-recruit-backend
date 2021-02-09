package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.UserAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.UserManager;
import com.xiaohuashifu.recruit.facade.service.vo.UserVO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 描述：用户管理器
 *
 * @author xhsf
 * @create 2021/1/26 12:46
 */
@Component
@CacheConfig(cacheNames = "default")
public class UserManagerImpl implements UserManager {

    private final UserAssembler userAssembler;

    @Reference
    private UserService userService;

    public UserManagerImpl(UserAssembler userAssembler) {
        this.userAssembler = userAssembler;
    }

    /**
     * 获取用户
     *
     * @param userId 用户编号
     * @return UserVO
     */
    @Cacheable(key = "'users:' + #userId")
    @Override
    public UserVO getUser(Long userId) {
        return userAssembler.userDTOToUserVO(userService.getUser(userId));
    }

}
