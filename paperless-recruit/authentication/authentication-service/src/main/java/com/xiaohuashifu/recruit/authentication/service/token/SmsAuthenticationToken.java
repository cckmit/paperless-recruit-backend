package com.xiaohuashifu.recruit.authentication.service.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 描述：短信验证码登录的 Token
 *
 * @author: xhsf
 * @create: 2020/11/11 17:47
 */
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 手机号码
     */
    private final String phone;

    /**
     * 短信验证码
     */
    private final String authCode;

    public SmsAuthenticationToken(String phone, String authCode) {
        super(null);
        this.phone = phone;
        this.authCode = authCode;
        setAuthenticated(false);
    }

    public SmsAuthenticationToken(String phone, String authCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phone = phone;
        this.authCode = authCode;
        super.setAuthenticated(true); // must use super, as we override
    }

    public String getPhone() {
        return phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getPrincipal() {
        throw new UnsupportedOperationException();
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
