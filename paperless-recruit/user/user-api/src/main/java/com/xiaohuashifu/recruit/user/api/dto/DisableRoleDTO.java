package com.xiaohuashifu.recruit.user.api.dto;

/**
 * 描述：禁用角色的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
public class DisableRoleDTO {

    /**
     * 角色
     */
    private RoleDTO role;

    /**
     * 被禁用的子角色数量
     */
    private Integer disabledCount;

    public DisableRoleDTO(RoleDTO role, Integer disabledCount) {
        this.role = role;
        this.disabledCount = disabledCount;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Integer getDisabledCount() {
        return disabledCount;
    }

    public void setDisabledCount(Integer disabledCount) {
        this.disabledCount = disabledCount;
    }

    @Override
    public String toString() {
        return "DisableRoleDTO{" +
                "role=" + role +
                ", disabledCount=" + disabledCount +
                '}';
    }
}
