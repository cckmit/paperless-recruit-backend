package com.xiaohuashifu.recruit.organization.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.ImageExtensionName;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 描述：更新部门 logo 的参数对象
 *
 * @author xhsf
 * @create 2020/12/13 13:50
 */
public class UpdateDepartmentLogoPO implements Serializable {

    /**
     * 部门编号
     */
    @NotNull(message = "The id can't be null.")
    @Positive(message = "The id must be greater than 0.")
    private Long id;

    /**
     * Logo
     */
    @NotNull(message = "The logo can't be null.")
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_LOGO_LENGTH, message = "The logo must not be greater than "
                    + DepartmentConstants.MAX_DEPARTMENT_LOGO_LENGTH + ".")
    private byte[] logo;

    /**
     * Logo 的扩展名
     */
    @NotBlank(message = "The logoExtensionName can't be blank.")
    @ImageExtensionName
    private String logoExtensionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoExtensionName() {
        return logoExtensionName;
    }

    public void setLogoExtensionName(String logoExtensionName) {
        this.logoExtensionName = logoExtensionName;
    }

    @Override
    public String toString() {
        return "UpdateDepartmentLogoPO{" +
                "id=" + id +
                ", logo=" + Arrays.toString(logo) +
                ", logoExtensionName='" + logoExtensionName + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private byte[] logo;
        private String logoExtensionName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public Builder logoExtensionName(String logoExtensionName) {
            this.logoExtensionName = logoExtensionName;
            return this;
        }

        public UpdateDepartmentLogoPO build() {
            UpdateDepartmentLogoPO updateDepartmentLogoPO = new UpdateDepartmentLogoPO();
            updateDepartmentLogoPO.setId(id);
            updateDepartmentLogoPO.setLogo(logo);
            updateDepartmentLogoPO.setLogoExtensionName(logoExtensionName);
            return updateDepartmentLogoPO;
        }
    }
}
