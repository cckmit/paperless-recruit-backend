package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 描述：报名表附件参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
public class ApplicationFormAttachmentPO implements Serializable {

    /**
     * 附件
     */
    @NotNull(message = "The avatar can't be null.")
    @Size(max = ApplicationFormConstants.MAX_ATTACHMENT_LENGTH, message = "The attachment must not be greater than "
            + ApplicationFormConstants.MAX_ATTACHMENT_LENGTH + ".")
    protected byte[] attachment;

    /**
     * 附件的名字
     */
    @NotBlank(message = "The attachmentName can't be blank.")
    @Size(max = ApplicationFormConstants.MAX_ATTACHMENT_NAME_LENGTH,
            message = "The attachmentName must not be greater than "
                    + ApplicationFormConstants.MAX_ATTACHMENT_NAME_LENGTH + ".")
    protected String attachmentName;

    public ApplicationFormAttachmentPO() {}

    public ApplicationFormAttachmentPO(byte[] attachment, String attachmentName) {
        this.attachment = attachment;
        this.attachmentName = attachmentName;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String toString() {
        return "ApplicationFormAttachmentPO{" +
                "attachment=" + Arrays.toString(attachment) +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }
}
