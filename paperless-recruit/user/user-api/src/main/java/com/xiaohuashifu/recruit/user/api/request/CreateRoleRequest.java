package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.user.api.constant.RoleConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：创建 Role 请求
 *
 * @author xhsf
 * @create 2020/12/9 17:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoleRequest implements Serializable {

    @NotNull
    @PositiveOrZero
    private Long parentRoleId;

    @NotBlank
    @Size(max = RoleConstants.MAX_ROLE_NAME_LENGTH)
    private String roleName;

    @NotBlank
    @Size(max = RoleConstants.MAX_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    private Boolean available;

}
