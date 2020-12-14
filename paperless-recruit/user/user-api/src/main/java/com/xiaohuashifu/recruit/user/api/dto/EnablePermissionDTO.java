package com.xiaohuashifu.recruit.user.api.dto;

/**
 * 描述：解禁权限的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
public class EnablePermissionDTO {

    /**
     * 权限
     */
    private PermissionDTO permission;

    /**
     * 被解禁的子权限数量
     */
    private Integer enabledCount;

    public EnablePermissionDTO(PermissionDTO permission, Integer enabledCount) {
        this.permission = permission;
        this.enabledCount = enabledCount;
    }

    public PermissionDTO getPermission() {
        return permission;
    }

    public void setPermission(PermissionDTO permission) {
        this.permission = permission;
    }

    public Integer getEnabledCount() {
        return enabledCount;
    }

    public void setEnabledCount(Integer enabledCount) {
        this.enabledCount = enabledCount;
    }

    @Override
    public String toString() {
        return "EnablePermissionDTO{" +
                "permission=" + permission +
                ", enabledCount=" + enabledCount +
                '}';
    }
}
