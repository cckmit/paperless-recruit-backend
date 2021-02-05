package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建并发送邮件验证码的请求
 *
 * @author: xhsf
 * @create: 2020/11/19 13:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAndSendEmailAuthCodeRequest implements Serializable {

    /**
     * 邮箱
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    @Size(max = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH)
    private String subject;

    /**
     * 标题，用于发送邮件验证码时标识该邮件验证码的目的
     * 如“找回密码”，“邮箱绑定”等
     */
    @NotBlank
    @Size(max = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_TITLE_LENGTH)
    private String title;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     */
    @NotNull
    @Positive
    @Max(value = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_EXPIRATION_TIME)
    private Integer expirationTime;

}
