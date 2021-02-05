package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 描述：通过短信验证码创建用户请求
 *
 * @author xhsf
 * @create 2021/2/5 17:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserBySmsAuthCodeRequest implements Serializable {

    /**
     * 手机号码
     */
    @NotBlank
    @Phone
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank
    @AuthCode
    private String authCode;

    /**
     * 密码
     */
    @NotEmpty
    @Password
    private String password;

}
