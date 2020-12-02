package com.xiaohuashifu.recruit.user.service.do0;

import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 描述：角色表映射对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Alias("role")
public class RoleDO {
    private Long id;

    private Long parentRoleId;

    private String roleName;

    private String description;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public RoleDO() {
    }

    public RoleDO(Long id, Long parentRoleId, String roleName, String description, Boolean available,
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
        return "RoleDO{" +
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

        public RoleDO build() {
            RoleDO roleDO = new RoleDO();
            roleDO.setId(id);
            roleDO.setParentRoleId(parentRoleId);
            roleDO.setRoleName(roleName);
            roleDO.setDescription(description);
            roleDO.setAvailable(available);
            roleDO.setCreateTime(createTime);
            roleDO.setUpdateTime(updateTime);
            return roleDO;
        }
    }
}
