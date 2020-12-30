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
public class UpdateApplicationFormAttachmentPO extends ApplicationFormAttachmentPO {

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
        return "UpdateApplicationFormAttachmentPO{" +
                "id=" + id +
                ", attachment=" + Arrays.toString(attachment) +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        protected byte[] attachment;
        protected String attachmentName;
        private Long id;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder attachment(byte[] attachment) {
            this.attachment = attachment;
            return this;
        }

        public Builder attachmentName(String attachmentName) {
            this.attachmentName = attachmentName;
            return this;
        }

        public UpdateApplicationFormAttachmentPO build() {
            UpdateApplicationFormAttachmentPO updateApplicationFormAttachmentPO =
                    new UpdateApplicationFormAttachmentPO();
            updateApplicationFormAttachmentPO.setId(id);
            updateApplicationFormAttachmentPO.setAttachment(attachment);
            updateApplicationFormAttachmentPO.setAttachmentName(attachmentName);
            return updateApplicationFormAttachmentPO;
        }
    }
}
