package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.user.api.constant.PermissionConstants;
import com.xiaohuashifu.recruit.user.api.constant.UserProfileConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新权限请求
 *
 * @author xhsf
 * @create 2021/2/6 01:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermissionRequest implements Serializable {

    /**
     * 权限编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 权限名
     */
    @NotAllCharactersBlank
    @Size(max = PermissionConstants.MAX_PERMISSION_NAME_LENGTH)
    private String permissionName;

    /**
     * 授权路径
     */
    @NotAllCharactersBlank
    @Size(max = PermissionConstants.MAX_AUTHORIZATION_URL_LENGTH)
    private String authorizationUrl;

    /**
     * 权限介绍
     */
    @NotAllCharactersBlank
    @Size(max = PermissionConstants.MAX_DESCRIPTION_LENGTH)
    private String description;

}
