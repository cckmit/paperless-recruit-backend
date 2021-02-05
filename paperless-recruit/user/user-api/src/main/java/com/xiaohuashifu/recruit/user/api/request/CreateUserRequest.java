package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 描述：创建用户请求
 *
 * @author xhsf
 * @create 2021/2/5 17:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest implements Serializable {

    /**
     * 用户名
     */
    @NotBlank
    @Username
    private String username;

    /**
     * 密码
     */
    @NotEmpty
    @Password
    private String password;

}
