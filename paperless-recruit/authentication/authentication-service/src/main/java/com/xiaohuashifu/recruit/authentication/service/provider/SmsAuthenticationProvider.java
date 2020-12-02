package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

/**
 * 描述：AuthenticationManager之后正在处理短信验证码登录的类
 *      接受SmsAuthenticationToken类型的Token并处理
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
                                     PermissionService permissionService) {
        super(permissionService);
        this.smsService = smsService;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected UserDTO check(Authentication authentication) {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        String phone = smsAuthenticationToken.getPhone();
        String authCode = smsAuthenticationToken.getAuthCode();

        // 验证短信验证码
        Result<Void> checkSmsAuthCodeResult = smsService.checkSmsAuthCode(
                new SmsAuthCodeDTO.Builder()
                        .phone(phone)
                        .subject(SUBJECT)
                        .authCode(authCode)
                        .delete(true)
                        .build());
        if (!checkSmsAuthCodeResult.isSuccess()) {
            throw new BadCredentialsException("Auth failed.");
        }

        // 获取用户对象
        return userService.getUserByPhone(phone).getData();
    }
}
