package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.user.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.authentication.service.token.OpenidAuthenticationToken;
import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：AuthenticationManager之后正在处理Openid登录的类
 *      接受OpenidAuthenticationToken类型的Token并处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 19:59
 */
public class OpenidAuthenticationProvider implements AuthenticationProvider {

    private final AuthOpenidService authOpenidService;

    private final UserService userService;

    private final PermissionService permissionService;

    public OpenidAuthenticationProvider(AuthOpenidService authOpenidService, UserService userService,
                                        PermissionService permissionService) {
        this.authOpenidService = authOpenidService;
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenidAuthenticationToken openidAuthenticationToken = (OpenidAuthenticationToken) authentication;
        App app = openidAuthenticationToken.getApp();
        String code = openidAuthenticationToken.getCode();

        // 验证短信验证码
        Result<AuthOpenidDTO> checkAuthOpenidForWechatMpResult =
                authOpenidService.checkAuthOpenidForWechatMp(app, code);
        if (!checkAuthOpenidForWechatMpResult.isSuccess()) {
            throw new BadCredentialsException("Auth error.");
        }
        AuthOpenidDTO authOpenidDTO = checkAuthOpenidForWechatMpResult.getData();

        // 获取用户对象
        UserDTO userDTO = userService.getUser(authOpenidDTO.getUserId()).getData();

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
        return OpenidAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
