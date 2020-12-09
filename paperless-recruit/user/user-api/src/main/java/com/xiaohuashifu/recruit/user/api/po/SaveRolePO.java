package com.xiaohuashifu.recruit.user.api.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：保存 Role 的参数对象
 *
 * @author xhsf
 * @create 2020/12/9 17:18
 */
public class SaveRolePO implements Serializable {

    @NotNull
    @PositiveOrZero
    private Long parentRoleId;

    @NotBlank
    @Size(min = 1, max = 64)
    private String roleName;

    @NotBlank
    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    private Boolean available;

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
        return "SaveRolePO{" +
                "parentRoleId=" + parentRoleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long parentRoleId;
        private String roleName;
        private String description;
        private Boolean available;

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

        public SaveRolePO build() {
            SaveRolePO saveRolePO = new SaveRolePO();
            saveRolePO.setParentRoleId(parentRoleId);
            saveRolePO.setRoleName(roleName);
            saveRolePO.setDescription(description);
            saveRolePO.setAvailable(available);
            return saveRolePO;
        }
    }
}
