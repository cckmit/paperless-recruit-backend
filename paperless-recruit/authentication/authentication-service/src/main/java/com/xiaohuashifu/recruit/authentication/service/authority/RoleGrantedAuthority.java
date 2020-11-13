package com.xiaohuashifu.recruit.authentication.service.authority;

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
public final class RoleGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String role;

    private final Set<String> permissionSet;

    public RoleGrantedAuthority(String role, Set<String> permissionSet) {
        this.permissionSet = permissionSet;
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof RoleGrantedAuthority) {
            return role.equals(((RoleGrantedAuthority) obj).role);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public String toString() {
        return "RoleGrantedAuthority{" +
                "role='" + role + '\'' +
                ", permissionSet=" + permissionSet +
                '}';
    }
}
