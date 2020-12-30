package com.xiaohuashifu.recruit.registration.api.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Arrays;

/**
 * 描述：更新报名表头像参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
public class UpdateApplicationFormAvatarPO extends ApplicationFormAvatarPO {

    /**
     * 报名表编号
     */
    @NotNull(message = "The id can't be null.")
    @Positive(message = "The id must be greater than 0.")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateApplicationFormAvatarPO{" +
                "id=" + id +
                ", avatar=" + Arrays.toString(avatar) +
                ", extensionName='" + extensionName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        protected byte[] avatar;
        protected String extensionName;
        private Long id;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder avatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder extensionName(String extensionName) {
            this.extensionName = extensionName;
            return this;
        }

        public UpdateApplicationFormAvatarPO build() {
            UpdateApplicationFormAvatarPO updateApplicationFormAvatarPO = new UpdateApplicationFormAvatarPO();
            updateApplicationFormAvatarPO.setId(id);
            updateApplicationFormAvatarPO.setAvatar(avatar);
            updateApplicationFormAvatarPO.setExtensionName(extensionName);
            return updateApplicationFormAvatarPO;
        }
    }
}
