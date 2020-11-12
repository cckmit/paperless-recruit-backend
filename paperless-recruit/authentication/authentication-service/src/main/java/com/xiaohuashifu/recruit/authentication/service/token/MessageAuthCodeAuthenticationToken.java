package com.xiaohuashifu.recruit.authentication.service.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 描述：短信验证码登录的Token
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 17:47
 */
public class MessageAuthCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private final Object credentials;

    public MessageAuthCodeAuthenticationToken(String phone, String authCode) {
        super(null);
        this.principal = phone;
        this.credentials = authCode;
        setAuthenticated(false);
    }

    public MessageAuthCodeAuthenticationToken(String phone, String authCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = phone;
        this.credentials = authCode;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
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
