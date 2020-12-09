package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：权限传输对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public class PermissionDTO implements Serializable {

    /**
     * 权限编号
     */
    private Long id;

    /**
     * 父权限编号，若为0表示没有父亲
     */
    private Long parentPermissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 授权路径
     */
    private String authorizationUrl;

    /**
     * 对权限的描述
     */
    private String description;

    /**
     * 权限是否可用
     */
    private Boolean available;

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

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "id=" + id +
                ", parentPermissionId=" + parentPermissionId +
                ", permissionName='" + permissionName + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long parentPermissionId;
        private String permissionName;
        private String authorizationUrl;
        private String description;
        private Boolean available;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder parentPermissionId(Long parentPermissionId) {
            this.parentPermissionId = parentPermissionId;
            return this;
        }

        public Builder permissionName(String permissionName) {
            this.permissionName = permissionName;
            return this;
        }

        public Builder authorizationUrl(String authorizationUrl) {
            this.authorizationUrl = authorizationUrl;
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

        public PermissionDTO build() {
            PermissionDTO permissionDTO = new PermissionDTO();
            permissionDTO.setId(id);
            permissionDTO.setParentPermissionId(parentPermissionId);
            permissionDTO.setPermissionName(permissionName);
            permissionDTO.setAuthorizationUrl(authorizationUrl);
            permissionDTO.setDescription(description);
            permissionDTO.setAvailable(available);
            return permissionDTO;
        }
    }
}
