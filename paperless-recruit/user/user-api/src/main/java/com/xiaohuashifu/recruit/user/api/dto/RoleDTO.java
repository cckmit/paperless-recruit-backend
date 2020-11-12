package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.group.Group;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：角色
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public class RoleDTO implements Serializable {
    @Id(groups = {Group.class})
    private Long id;

    @Id(groups = {Group.class})
    private Long parentRoleId;

    // TODO: 2020/11/12 这里可以定义角色名校验注解
    private String roleName;

    // TODO: 2020/11/12 这里可以定义校验注解
    private String description;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public RoleDTO() {
    }

    public RoleDTO(Long id, Long parentRoleId, String roleName, String description, Boolean available,
                   LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.parentRoleId = parentRoleId;
        this.roleName = roleName;
        this.description = description;
        this.available = available;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", parentRoleId=" + parentRoleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private Long parentRoleId;
        private String roleName;
        private String description;
        private Boolean available;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder() {
        }

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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public RoleDTO build() {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(id);
            roleDTO.setParentRoleId(parentRoleId);
            roleDTO.setRoleName(roleName);
            roleDTO.setDescription(description);
            roleDTO.setAvailable(available);
            roleDTO.setCreateTime(createTime);
            roleDTO.setUpdateTime(updateTime);
            return roleDTO;
        }
    }
}
