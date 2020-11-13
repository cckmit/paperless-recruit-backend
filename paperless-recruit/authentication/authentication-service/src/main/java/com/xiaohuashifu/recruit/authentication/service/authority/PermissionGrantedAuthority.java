package com.xiaohuashifu.recruit.authentication.service.authority;

import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * 描述：角色权限对象
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/13 00:26
 */
public final class PermissionGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final PermissionDTO permission;

    public PermissionGrantedAuthority(PermissionDTO permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof PermissionGrantedAuthority) {
            return permission.getPermissionName().equals(((PermissionGrantedAuthority) obj).permission.getPermissionName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return permission.getPermissionName().hashCode();
    }

    @Override
    public String toString() {
        return permission.toString();
    }
}
