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

    /**
     * 父权限编号，若0标识没有父亲
     */
    @NotNull(message = "The parentPermissionId can't be null.")
    @PositiveOrZero(message = "The parentPermissionId must be greater than or equal to 0.")
    private Long parentPermissionId;

    /**
     * 权限名
     */
    @NotBlank(message = "The permissionName can't be blank.")
    @Size(min = 1, max = 64, message = "The length of permissionName must be between 1 and 64.")
    private String permissionName;

    /**
     * 授权路径，可以是 AntPath
     */
    @NotBlank(message = "The authorizationUrl can't be blank.")
    @Size(min = 1, max = 255, message = "The length of authorizationUrl must be between 1 and 255.")
    private String authorizationUrl;

    /**
     * 对权限的描述
     */
    @NotBlank(message = "The description can't be blank.")
    @Size(min = 1, max = 200, message = "The length of description must be between 1 and 200.")
    private String description;

    /**
     * 权限是否可用
     */
    @NotNull(message = "The available can't be null.")
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
