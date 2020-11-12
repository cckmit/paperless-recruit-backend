package com.xiaohuashifu.recruit.user.service.pojo.do0;

import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 描述：角色
 *
 * @author: xhsf
 * @email: 827032783@qq.com
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

}
