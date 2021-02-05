package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.token.OpenIdAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;

/**
 * 描述：AuthenticationManager 之后正在处理 OpenId 登录的类
 *      接受 OpenIdAuthenticationToken 类型的 Token 并处理
 *
 * @author: xhsf
 * @create: 2020/11/21 19:59
 */
public class OpenIdAuthenticationProvider extends AbstractAuthenticationProvider {

    private final AuthOpenIdService authOpenIdService;

    private final UserService userService;

    public OpenIdAuthenticationProvider(AuthOpenIdService authOpenIdService, UserService userService,
                                        AuthorityService authorityService) {
        super(authorityService);
        this.authOpenIdService = authOpenIdService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected Long check(Authentication authentication) {
        OpenIdAuthenticationToken openIdAuthenticationToken = (OpenIdAuthenticationToken) authentication;
        AppEnum app = openIdAuthenticationToken.getApp();
        String code = openIdAuthenticationToken.getCode();

        // 通过 openId 认证
        AuthOpenIdDTO authOpenIdDTO;
        try {
            authOpenIdDTO = authOpenIdService.checkAuthOpenIdForWeChatMp(app, code);
        } catch (ServiceException e) {
            throw new BadCredentialsException("Auth failed.");
        }

        // 判断用户是否可用
        UserDTO userDTO = userService.getUser(authOpenIdDTO.getUserId());
        if (!userDTO.getAvailable()) {
            throw new DisabledException("The user unavailable.");
        }

        return authOpenIdDTO.getId();
    }

}
