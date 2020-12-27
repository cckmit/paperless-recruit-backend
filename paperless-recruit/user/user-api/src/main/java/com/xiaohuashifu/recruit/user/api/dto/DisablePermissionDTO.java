package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：禁用权限的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
public class DisablePermissionDTO implements Serializable {

    /**
     * 权限
     */
    private PermissionDTO permission;

    /**
     * 被禁用的子权限数量
     */
    private Integer disabledCount;

    public DisablePermissionDTO(PermissionDTO permission, Integer disabledCount) {
        this.permission = permission;
        this.disabledCount = disabledCount;
    }

    public PermissionDTO getPermission() {
        return permission;
    }

    public void setPermission(PermissionDTO permission) {
        this.permission = permission;
    }

    public Integer getDisabledCount() {
        return disabledCount;
    }

    public void setDisabledCount(Integer disabledCount) {
        this.disabledCount = disabledCount;
    }

    @Override
    public String toString() {
        return "DisablePermissionDTO{" +
                "permission=" + permission +
                ", disabledCount=" + disabledCount +
                '}';
    }
}
