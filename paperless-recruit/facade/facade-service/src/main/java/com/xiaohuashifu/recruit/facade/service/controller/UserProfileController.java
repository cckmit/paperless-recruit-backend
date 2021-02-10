package com.xiaohuashifu.recruit.facade.service.controller;

import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.UserProfileManager;
import com.xiaohuashifu.recruit.facade.service.vo.UserProfileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    public UserProfileController(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @ApiOperation(value = "获取用户信息", notes = "Required: userId = principal.id")
    @GetMapping("/users/{userId}/profiles")
    @Owner(id = "#userId", context = UserContext.class)
    public UserProfileVO getUserProfile(@ApiParam("用户编号") @PathVariable Long userId) {
        return userProfileManager.getUserProfile(userId);
    }

}
