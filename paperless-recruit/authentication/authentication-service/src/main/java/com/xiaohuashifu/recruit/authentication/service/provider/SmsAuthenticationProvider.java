package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 描述：AuthenticationManager之后正在处理短信验证码登录的类
 *      接受SmsAuthenticationToken类型的Token并处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 19:59
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final SmsLoginService phoneLoginService;

    private final UserService userService;

    public SmsAuthenticationProvider(SmsLoginService phoneLoginService, UserService userService) {
        this.phoneLoginService = phoneLoginService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String phone = (String) authenticationToken.getPrincipal();
        String authCode = (String) authenticationToken.getCredentials();
        Result<Void> checkSmsAuthCodeResult = phoneLoginService.checkSmsAuthCode(phone, authCode);
        // 没有通过校验
        if (!checkSmsAuthCodeResult.isSuccess()) {
            throw new InternalAuthenticationServiceException("Auth error.");
        }


//        UserDTO getUserResult = userService.getUserByPhone(phone).getData();

        return new SmsAuthenticationToken(
                phone, authCode, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
