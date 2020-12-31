package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：报名表附件参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
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
}
