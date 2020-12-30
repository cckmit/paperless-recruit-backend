package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.ImageExtensionName;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 描述：报名表头像参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
public class ApplicationFormAvatarPO implements Serializable {

    /**
     * 头像
     */
    @NotNull(message = "The avatar can't be null.")
    @Size(max = ApplicationFormConstants.MAX_AVATAR_LENGTH, message = "The avatar must not be greater than "
            + ApplicationFormConstants.MAX_AVATAR_LENGTH + ".")
    protected byte[] avatar;

    /**
     * 头像的扩展名
     */
    @NotBlank(message = "The extensionName can't be blank.")
    @ImageExtensionName
    protected String extensionName;

    public ApplicationFormAvatarPO() {
    }

    public ApplicationFormAvatarPO(byte[] avatar, String extensionName) {
        this.avatar = avatar;
        this.extensionName = extensionName;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    @Override
    public String toString() {
        return "ApplicationFormAvatarPO{" +
                "avatar=" + Arrays.toString(avatar) +
                ", extensionName='" + extensionName + '\'' +
                '}';
    }

}
