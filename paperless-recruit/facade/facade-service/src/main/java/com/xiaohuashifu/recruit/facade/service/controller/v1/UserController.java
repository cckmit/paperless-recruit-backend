package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorCodeUtils;
import com.xiaohuashifu.recruit.common.result.ErrorResponseUtils;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.UserAssembler;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.UserManager;
import com.xiaohuashifu.recruit.facade.service.vo.UserVO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @Reference
    private UserService userService;

    private final UserAssembler userAssembler;

    private final UserManager userManager;

    private final UserContext userContext;

    /**
     * 短信验证码方式注册
     * 这个方式会随机产生用户名
     */
    public static final String SIGN_UP_TYPE_SMS = "sms";

    /**
     * 用户名密码方式注册
     */
    public static final String SIGN_UP_TYPE_PASSWORD = "password";

    public UserController(UserAssembler userAssembler, UserManager userManager, UserContext userContext) {
        this.userAssembler = userAssembler;
        this.userManager = userManager;
        this.userContext = userContext;
    }

    /**
     * 用于创建用户，也就是注册
     *
     * @param params
     *          type 注册类型，可以为"sms", "password"
     *              其中对外只开放"sms"类型，sms类型会随机创建用户名
     *              "password"类型需要"admin"角色才可调用
     *          username: 用户名[password]
     *          password: 密码[sms, password]
     *          phone: 手机号码[sms]
     *          authCode: 短信验证码[sms]
     *
     * @return 用户对象
     */
    @PostMapping("/users")
    @PreAuthorize("(#params.get('type').equals('sms')) " +
            "or #params.get('type').equals('password') and hasRole('admin')")
    public Object post(@RequestBody Map<String, String> params) {
        // 短信验证码方式注册
        if (params.get("type").equals(SIGN_UP_TYPE_SMS)) {
            Result<UserDTO> signUpBySmsAuthCodeResult = userService.signUpBySmsAuthCode(
                    params.get("phone"), params.get("authCode"), params.get("password"));
            // 注册失败
            if (!signUpBySmsAuthCodeResult.isSuccess()) {
                System.out.println(signUpBySmsAuthCodeResult);
                String message = "注册失败";
                if (ErrorCodeUtils.equals(signUpBySmsAuthCodeResult.getErrorCode(),
                        ErrorCodeEnum.OPERATION_CONFLICT)) {
                    message = "手机号码已经存在";
                }
                if (ErrorCodeUtils.equals(signUpBySmsAuthCodeResult.getErrorCode(),
                        ErrorCodeEnum.INVALID_PARAMETER)) {
                    message = "手机号码或验证码或密码格式错误";
                }
                if (ErrorCodeUtils.equals(signUpBySmsAuthCodeResult.getErrorCode(),
                        ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND)) {
                    message = "找不到对应的验证码，请重新发送";
                }
                if (ErrorCodeUtils.equals(signUpBySmsAuthCodeResult.getErrorCode(),
                        ErrorCodeEnum.INVALID_PARAMETER_INCORRECT)) {
                    message = "验证码错误";
                }
                return ErrorResponseUtils.instanceResponseEntity(signUpBySmsAuthCodeResult.getErrorCode(), message);
            }
            // 注册成功
            return userAssembler.userDTOToUserVO(signUpBySmsAuthCodeResult.getData());
        }

        // 密码方式注册
        if (params.get("type").equals(SIGN_UP_TYPE_PASSWORD)) {
            Result<UserDTO> signUpUserResult = userService.signUpUser(params.get("username"), params.get("password"));
            // 注册失败
            if (!signUpUserResult.isSuccess()) {
                String message = "注册失败";
                if (ErrorCodeUtils.equals(signUpUserResult.getErrorCode(), ErrorCodeEnum.INVALID_PARAMETER)) {
                    message = "用户名或密码格式错误";
                }
                if (ErrorCodeUtils.equals(signUpUserResult.getErrorCode(), ErrorCodeEnum.OPERATION_CONFLICT)) {
                    message = "用户名已经存在";
                }
                return ErrorResponseUtils.instanceResponseEntity(signUpUserResult.getErrorCode(), message);
            }
            // 注册成功
            return userAssembler.userDTOToUserVO(signUpUserResult.getData());
        }

        // 不支持的注册类型
        return ErrorResponseUtils.instanceResponseEntity(
                ErrorCodeEnum.INVALID_PARAMETER_UNSUPPORTED, "注册类型错误");
    }

    /**
     * 获取已经认证的用户
     *
     * @return 用户
     */
    @ApiOperation(value = "获取已经认证的用户")
    @GetMapping("/user")
    public UserVO getAuthenticatedUser() {
        return userManager.getUser(userContext.getUserId());
    }

}
