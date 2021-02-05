package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：检查邮件验证码的请求
 *
 * @author: xhsf
 * @create: 2020/11/19 13:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckEmailAuthCodeRequest implements Serializable {

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
     * 邮箱验证码
     */
    @NotBlank
    @AuthCode
    private String authCode;

    /**
     * 检查成功后是否删除该键
     */
    @NotNull
    private Boolean delete;

}
