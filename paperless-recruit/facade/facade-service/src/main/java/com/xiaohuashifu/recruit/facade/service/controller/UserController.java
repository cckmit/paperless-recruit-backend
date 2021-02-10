package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.UserManager;
import com.xiaohuashifu.recruit.facade.service.vo.UserVO;
import com.xiaohuashifu.recruit.user.api.request.CreateUserBySmsAuthCodeRequest;
import com.xiaohuashifu.recruit.user.api.request.CreateUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：用户的门面类
 *
 * @author xhsf
 * @create 2020/11/28 14:41
 */
@ApiSupport(author = "XHSF")
@Api(tags = "用户")
@RestController
public class UserController {

    private final UserManager userManager;

    private final UserContext userContext;

    public UserController(UserManager userManager, UserContext userContext) {
        this.userManager = userManager;
        this.userContext = userContext;
    }

    @ApiOperation(value = "通过短信验证码注册用户")
    @PostMapping("/user/register/sms-auth-code")
    public UserVO registerBySmsAuthCode(@RequestBody CreateUserBySmsAuthCodeRequest request) {
        return userManager.registerBySmsAuthCode(request);
    }

    @ApiOperation(value = "注册用户", notes = "Role: root")
    @PostMapping("/user/register")
    @PreAuthorize("hasRole('root')")
    public UserVO register(@RequestBody CreateUserRequest request) {
        return userManager.register(request);
    }

    @ApiOperation(value = "获取已经认证的用户")
    @GetMapping("/authentication/user")
    public UserVO getAuthenticatedUser() {
        return userManager.getUser(userContext.getUserId());
    }

}
