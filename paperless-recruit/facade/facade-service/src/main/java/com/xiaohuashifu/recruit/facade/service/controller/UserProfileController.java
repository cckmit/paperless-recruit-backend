package com.xiaohuashifu.recruit.facade.service.controller;

import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.UserProfileManager;
import com.xiaohuashifu.recruit.facade.service.vo.UserProfileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：用户信息控制器
 *
 * @author xhsf
 * @create 2021/1/9 00:43
 */
@Api(tags = "用户信息")
@RestController
public class UserProfileController {

    private final UserProfileManager userProfileManager;

    private final UserContext userContext;

    public UserProfileController(UserProfileManager userProfileManager, UserContext userContext) {
        this.userProfileManager = userProfileManager;
        this.userContext = userContext;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "ROLE: user. Required: userId = principal.id")
    @GetMapping("users/{userId}/profiles")
    @PreAuthorize("hasRole('user')")
    public UserProfileVO getUserProfile(@ApiParam("用户编号") @PathVariable Long userId) {
        userContext.isOwner(userId);
        return userProfileManager.getUserProfile(userId);
    }

}
