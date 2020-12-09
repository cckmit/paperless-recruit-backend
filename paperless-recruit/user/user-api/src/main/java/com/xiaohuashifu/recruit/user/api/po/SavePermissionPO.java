package com.xiaohuashifu.recruit.user.api.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：保存 Permission 时的参数对象
 *
 * @author xhsf
 * @create 2020/12/9 17:09
 */
public class SavePermissionPO implements Serializable {

    @NotNull
    @PositiveOrZero
    private Long parentPermissionId;

    @NotBlank
    @Size(min = 1, max = 64)
    private String permissionName;

    /**
     * 授权路径，可以是 AntPath
     */
    @NotBlank
    @Size(min = 1, max = 255)
    private String authorizationUrl;

    @NotBlank
    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    private Boolean available;

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
        return "SavePermissionPO{" +
                "parentPermissionId=" + parentPermissionId +
                ", permissionName='" + permissionName + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long parentPermissionId;
        private String permissionName;
        private String authorizationUrl;
        private String description;
        private Boolean available;

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

        public SavePermissionPO build() {
            SavePermissionPO savePermissionPO = new SavePermissionPO();
            savePermissionPO.setParentPermissionId(parentPermissionId);
            savePermissionPO.setPermissionName(permissionName);
            savePermissionPO.setAuthorizationUrl(authorizationUrl);
            savePermissionPO.setDescription(description);
            savePermissionPO.setAvailable(available);
            return savePermissionPO;
        }
    }
}
