package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.constant.AuthorityConstants;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：抽象 AuthenticationProvider，提供获取用户权限列表的能力
 *
 * @author: xhsf
 * @create: 2020/12/02 19:59
 */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    private final AuthorityService authorityService;

    public AbstractAuthenticationProvider(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDTO userDTO = check(authentication);

        // 获取权限列表
        Set<String> authoritySet = authorityService.listAuthoritiesByUserId(
                userDTO.getId(), AuthorityConstants.SPRING_SECURITY_ROLE_PREFIX).getData();
        List<SimpleGrantedAuthority> authorityList = authoritySet.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 封装成用户名的 Token
        return new UsernamePasswordAuthenticationToken(userDTO.getUsername(), null, authorityList);
    }

    /**
     * 检查用户权限，若没有通过认证，直接抛出异常即可
     *
     * @param authentication 认证对象
     * @return UserDTO 用户 DTO 对象，用于获取用户编号，用户名
     */
    protected abstract UserDTO check(Authentication authentication);
}
