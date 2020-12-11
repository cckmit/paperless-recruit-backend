package com.xiaohuashifu.recruit.authentication.service.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 描述：Password 登录的 Token
 *
 * @author: xhsf
 * @create: 2020/12/12 17:47
 */
public class PasswordAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 主体，可以是用户名、手机号、邮箱
     */
    private final String principal;

    /**
     * 密码
     */
    private final String password;

    public PasswordAuthenticationToken(String principal, String password) {
        super(null);
        this.principal = principal;
        this.password = password;
        setAuthenticated(false);
    }

    public PasswordAuthenticationToken(String principal, String password,
                                       Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.password = password;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
