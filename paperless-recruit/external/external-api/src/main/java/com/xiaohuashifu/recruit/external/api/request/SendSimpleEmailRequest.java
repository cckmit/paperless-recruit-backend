package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述：发送简单邮件请求
 *
 * @author xhsf
 * @create 2020/12/9 20:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendSimpleEmailRequest implements Serializable {

    /**
     * 目标邮箱
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 主题，也就是邮件的标题
     */
    @NotBlank
    @Size(max = EmailServiceConstants.MAX_EMAIL_SUBJECT_LENGTH)
    private String subject;

    /**
     * 内容
     */
    @NotBlank
    @Size(max = EmailServiceConstants.MAX_EMAIL_TEXT_LENGTH)
    private String text;

    /**
     * 附件
     */
    @Size(max = EmailServiceConstants.MAX_EMAIL_ATTACHMENT_NUMBER)
    private Map<String, byte[]> attachmentMap;

}
