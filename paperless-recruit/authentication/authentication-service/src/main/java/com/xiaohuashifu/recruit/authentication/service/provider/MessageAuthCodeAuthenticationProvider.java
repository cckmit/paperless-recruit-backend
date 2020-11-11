package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import com.xiaohuashifu.recruit.authentication.service.pojo.token.MessageAuthCodeAuthenticationToken;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 描述：AuthenticationManager之后正在处理短信验证码登录的类
 *      接受MessageAuthCodeAuthenticationToken类型的Token并处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 19:59
 */
public class MessageAuthCodeAuthenticationProvider implements AuthenticationProvider {

    private final PhoneLoginService phoneLoginService;

    private final UserService userService;

    public MessageAuthCodeAuthenticationProvider(PhoneLoginService phoneLoginService, UserService userService) {
        this.phoneLoginService = phoneLoginService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MessageAuthCodeAuthenticationToken authenticationToken = (MessageAuthCodeAuthenticationToken) authentication;
        String phone = authenticationToken.getPhone();
        String authCode = authenticationToken.getAuthCode();
        System.out.println(phone + ":" + authCode);
        Result<Void> checkMessageAuthCodeResult = phoneLoginService.checkMessageAuthCode(phone, authCode);
        // 没有通过校验
        if (!checkMessageAuthCodeResult.isSuccess()) {
            throw new InternalAuthenticationServiceException("Auth error.");
        }


//        UserDTO getUserResult = userService.getUserByPhone(phone).getData();

        return new MessageAuthCodeAuthenticationToken(
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"), phone, authCode);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MessageAuthCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
