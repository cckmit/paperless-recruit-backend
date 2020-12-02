package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.user.api.service.PermissionService;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：权限
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public class PermissionDTO implements Serializable {
    @Positive
    private Long id;

    @PositiveOrZero
    @NotNull(groups = PermissionService.SavePermission.class)
    private Long parentPermissionId;

    @Size(min = 1, max = 64)
    @NotBlank(groups = PermissionService.SavePermission.class)
    private String permissionName;

    /**
     * 授权路径，可以是 AntPath
     */
    @Size(min = 1, max = 255)
    @NotBlank(groups = PermissionService.SavePermission.class)
    private String authorizationUrl;

    @Size(min = 1, max = 200)
    @NotBlank(groups = PermissionService.SavePermission.class)
    private String description;

    @NotNull(groups = PermissionService.SavePermission.class)
    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public PermissionDTO() {
    }

    public PermissionDTO(Long id, Long parentPermissionId, String permissionName, String authorizationUrl,
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
        return "PermissionDTO{" +
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


    public static final class Builder {
        private Long id;
        private Long parentPermissionId;
        private String permissionName;
        private String authorizationUrl;
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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
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
            permissionDTO.setCreateTime(createTime);
            permissionDTO.setUpdateTime(updateTime);
            return permissionDTO;
        }
    }
}
