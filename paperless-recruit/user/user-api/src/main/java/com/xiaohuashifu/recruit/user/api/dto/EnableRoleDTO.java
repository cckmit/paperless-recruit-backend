package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：解禁角色的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
public class EnableRoleDTO implements Serializable {

    /**
     * 角色
     */
    private RoleDTO role;

    /**
     * 被解禁的子角色数量
     */
    private Integer enabledCount;

    public EnableRoleDTO(RoleDTO role, Integer enabledCount) {
        this.role = role;
        this.enabledCount = enabledCount;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Integer getEnabledCount() {
        return enabledCount;
    }

    public void setEnabledCount(Integer enabledCount) {
        this.enabledCount = enabledCount;
    }

    @Override
    public String toString() {
        return "EnableRoleDTO{" +
                "role=" + role +
                ", enabledCount=" + enabledCount +
                '}';
    }
}
