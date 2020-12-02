package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.token.OpenidAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

/**
 * 描述：AuthenticationManager之后正在处理Openid登录的类
 *      接受OpenidAuthenticationToken类型的Token并处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 19:59
 */
public class OpenidAuthenticationProvider extends AbstractAuthenticationProvider {

    private final AuthOpenidService authOpenidService;

    private final UserService userService;

    public OpenidAuthenticationProvider(AuthOpenidService authOpenidService, UserService userService,
                                        PermissionService permissionService) {
        super(permissionService);
        this.authOpenidService = authOpenidService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenidAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected UserDTO check(Authentication authentication) {
        OpenidAuthenticationToken openidAuthenticationToken = (OpenidAuthenticationToken) authentication;
        AppEnum app = openidAuthenticationToken.getApp();
        String code = openidAuthenticationToken.getCode();

        // 验证短信验证码
        Result<AuthOpenidDTO> checkAuthOpenidForWechatMpResult =
                authOpenidService.checkAuthOpenidForWechatMp(app, code);
        if (!checkAuthOpenidForWechatMpResult.isSuccess()) {
            throw new BadCredentialsException("Auth failed.");
        }
        AuthOpenidDTO authOpenidDTO = checkAuthOpenidForWechatMpResult.getData();

        // 获取用户对象
        return userService.getUser(authOpenidDTO.getUserId()).getData();
    }
}
