package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.external.api.request.CheckSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 描述：AuthenticationManager 之后正在处理短信验证码登录的类
 *      接受 SmsAuthenticationToken 类型的 Token 并处理
 *
 * @author: xhsf
 * @create: 2020/11/11 19:59
 */
public class SmsAuthenticationProvider extends AbstractAuthenticationProvider {

    /**
     * 短信验证码登录的主题，用于调用发送和检验短信验证码服务
     */
    public static final String SUBJECT = "authentication:sms-sign-in";

    private final SmsService smsService;

    private final UserService userService;

    public SmsAuthenticationProvider(SmsService smsService, UserService userService,
                                     AuthorityService authorityService) {
        super(authorityService);
        this.smsService = smsService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected Long check(Authentication authentication) {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        String phone = smsAuthenticationToken.getPhone();
        String authCode = smsAuthenticationToken.getAuthCode();

        // 通过短信验证码认证
        try {
           smsService.checkSmsAuthCode(
                   CheckSmsAuthCodeRequest.builder()
                           .phone(phone)
                           .subject(SUBJECT)
                           .authCode(authCode)
                           .delete(true)
                           .build());
        } catch (ServiceException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        // 获取用户对象
        UserDTO userDTO;
        try {
            userDTO = userService.getUserByPhone(phone);
        } catch (NotFoundServiceException e) {
            throw new UsernameNotFoundException("The user does not exist.");
        }

        // 判断用户是否可用
        if (!userDTO.getAvailable()) {
            throw new DisabledException("The user unavailable.");
        }

        return userDTO.getId();
    }

}
