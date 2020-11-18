package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import com.xiaohuashifu.recruit.authentication.service.token.SmsAuthenticationToken;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
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

    // TODO: 2020/11/18 继续完善 权限，用户信息
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

        UserDTO userDTO = userService.getUserByPhone(phone).getData();

        // 获取权限列表
        Result<List<PermissionDTO>> getPermissionResult = permissionService.getPermissionByUserId(userDTO.getId());
        List<PermissionDTO> permissionDTOList = getPermissionResult.getData();
        List<SimpleGrantedAuthority> authorityList = permissionDTOList.stream()
                .map(permissionDTO -> new SimpleGrantedAuthority(permissionDTO.getPermissionName()))
                .collect(Collectors.toList());

        // 封装成用户名的Token
        return new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword(), authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
