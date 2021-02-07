package com.xiaohuashifu.recruit.authentication.service.provider;

import com.xiaohuashifu.recruit.authentication.service.constant.AuthorityConstants;
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
        // 检查用户是否通过认证
        Long userId = check(authentication);

        // 获取权限列表
        Set<String> authoritySet = authorityService.listAuthoritiesByUserId(
                userId, AuthorityConstants.SPRING_SECURITY_ROLE_PREFIX);
        List<SimpleGrantedAuthority> authorityList = authoritySet.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 封装成用户名的 Token
        return new UsernamePasswordAuthenticationToken(userId, null, authorityList);
    }

    /**
     * 检查用户权限，若没有通过认证，直接抛出异常即可
     *
     * @param authentication 认证对象
     * @return Long 用户编号
     */
    protected abstract Long check(Authentication authentication);

}
