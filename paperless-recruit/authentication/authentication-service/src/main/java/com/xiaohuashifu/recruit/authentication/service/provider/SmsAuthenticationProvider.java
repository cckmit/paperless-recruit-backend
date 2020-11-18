package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：AuthenticationManager之后正在处理短信验证码登录的类
 *      接受SmsAuthenticationToken类型的Token并处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 19:59
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private final SmsLoginService smsLoginService;

    private final UserService userService;

    private final PermissionService permissionService;

    public SmsAuthenticationProvider(SmsLoginService smsLoginService, UserService userService,
                                     PermissionService permissionService) {
        this.smsLoginService = smsLoginService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        String phone = (String) smsAuthenticationToken.getPrincipal();
        String authCode = (String) smsAuthenticationToken.getCredentials();

        // 验证短信验证码
        Result<Void> checkSmsAuthCodeResult = smsLoginService.checkSmsAuthCode(phone, authCode);
        if (!checkSmsAuthCodeResult.isSuccess()) {
            throw new InternalAuthenticationServiceException("Auth error.");
        }

        // 获取用户对象
        UserDTO userDTO = userService.getUserByPhone(phone).getData();

        // 获取权限列表
        Set<String> authoritySet = permissionService.getAuthorityByUserId(userDTO.getId()).getData();
        List<SimpleGrantedAuthority> authorityList = authoritySet.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 封装成用户名的Token
        return new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(), authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
