package com.xiaohuashifu.recruit.authentication.service.pojo.token;

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

    private final String phone;
    private final String authCode;

    public MessageAuthCodeAuthenticationToken(String phone, String authCode) {
        super(null);
        this.phone = phone;
        this.authCode = authCode;
        setAuthenticated(false);
    }

    public MessageAuthCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String phone, String authCode) {
        super(authorities);
        this.phone = phone;
        this.authCode = authCode;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return authCode;
    }

    @Override
    public String getPrincipal() {
        return phone;
    }

}
