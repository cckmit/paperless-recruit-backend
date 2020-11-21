package com.xiaohuashifu.recruit.authentication.service.token;

import com.xiaohuashifu.recruit.common.constant.App;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 描述：Openid登录的Token
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 17:47
 */
public class OpenidAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 具体的app
     */
    private final App app;

    /**
     * 微信小程序登录时需要带上的code
     */
    private final String code;

    public OpenidAuthenticationToken(App app, String code) {
        super(null);
        this.app = app;
        this.code = code;
        setAuthenticated(false);
    }

    public OpenidAuthenticationToken(App app, String code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.app = app;
        this.code = code;
        super.setAuthenticated(true); // must use super, as we override
    }

    public App getApp() {
        return app;
    }

    public String getCode() {
        return code;
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
