package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.external.api.constant.SmsServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：检查短信验证码请求
 *
 * @author xhsf
 * @create 2020/12/9 20:19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckSmsAuthCodeRequest implements Serializable {

    /**
     * 手机号码
     */
    @NotBlank
    @Phone
    private String phone;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    @Size(max = SmsServiceConstants.MAX_SMS_AUTH_CODE_SUBJECT_LENGTH)
    private String subject;

    /**
     * 短信验证码
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
