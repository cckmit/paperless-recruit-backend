package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.token.PasswordAuthenticationToken;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 描述：AuthenticationManager 之后正在处理 Password 登录的类
 *      接受 OpenIdAuthenticationToken 类型的 Token 并处理
 *
 * @author: xhsf
 * @create: 2020/11/21 19:59
 */
public class PasswordAuthenticationProvider extends AbstractAuthenticationProvider {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public PasswordAuthenticationProvider(UserService userService, AuthorityService authorityService,
                                          PasswordEncoder passwordEncoder) {
        super(authorityService);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    protected Long check(Authentication authentication) {
        PasswordAuthenticationToken passwordAuthenticationToken = (PasswordAuthenticationToken) authentication;
        String principal = passwordAuthenticationToken.getPrincipal();

        // 获取用户对象
        UserDTO userDTO;
        try {
            userDTO = userService.getUserByUsernameOrPhoneOrEmail(principal);
        } catch (NotFoundServiceException e) {
            throw new UsernameNotFoundException("The user does not exist.");
        }

        // 判断用户是否可用
        if (!userDTO.getAvailable()) {
            throw new DisabledException("The user unavailable.");
        }

        // 通过密码认证
        String password = passwordAuthenticationToken.getPassword();
        if (!passwordEncoder.matches(password, userDTO.getPassword())) {
            throw new BadCredentialsException("Password error.");
        }

        return userDTO.getId();
    }

}
