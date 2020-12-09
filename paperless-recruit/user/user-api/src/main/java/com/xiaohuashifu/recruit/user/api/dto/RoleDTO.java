package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：角色传输对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public class RoleDTO implements Serializable {

    /**
     * 角色编号
     */
    private Long id;

    /**
     * 父角色编号，若为0表示没有父亲
     */
    private Long parentRoleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 对角色的描述
     */
    private String description;

    /**
     * 角色是否可用
     */
    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", parentRoleId=" + parentRoleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long parentRoleId;
        private String roleName;
        private String description;
        private Boolean available;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder parentRoleId(Long parentRoleId) {
            this.parentRoleId = parentRoleId;
            return this;
        }

        public Builder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public RoleDTO build() {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(id);
            roleDTO.setParentRoleId(parentRoleId);
            roleDTO.setRoleName(roleName);
            roleDTO.setDescription(description);
            roleDTO.setAvailable(available);
            return roleDTO;
        }
    }
}
