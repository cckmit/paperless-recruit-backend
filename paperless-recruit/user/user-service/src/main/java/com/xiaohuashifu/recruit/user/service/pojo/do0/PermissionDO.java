package com.xiaohuashifu.recruit.user.service.pojo.do0;

import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 描述：权限
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
@Alias("permission")
public class PermissionDO {
    private Long id;

    private Long parentPermissionId;

    private String permissionName;

    private String authorizationUrl;

    private String description;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public PermissionDO() {
    }

    public PermissionDO(Long id, Long parentPermissionId, String permissionName, String authorizationUrl,
                        String description, Boolean available, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.parentPermissionId = parentPermissionId;
        this.permissionName = permissionName;
        this.authorizationUrl = authorizationUrl;
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

    public Long getParentPermissionId() {
        return parentPermissionId;
    }

    public void setParentPermissionId(Long parentPermissionId) {
        this.parentPermissionId = parentPermissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
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
        return "PermissionDO{" +
                "id=" + id +
                ", parentPermissionId=" + parentPermissionId +
                ", permissionName='" + permissionName + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
