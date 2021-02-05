package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.user.api.constant.PermissionConstants;
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
 * 描述：创建 Permission 请求
 *
 * @author xhsf
 * @create 2020/12/9 17:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePermissionRequest implements Serializable {

    /**
     * 父权限编号，若 0 表示没有父亲
     */
    @NotNull
    @PositiveOrZero
    private Long parentPermissionId;

    /**
     * 权限名
     */
    @NotBlank
    @Size(max = PermissionConstants.MAX_PERMISSION_NAME_LENGTH)
    private String permissionName;

    /**
     * 授权路径，可以是 AntPath
     */
    @NotBlank
    @Size(max = PermissionConstants.MAX_AUTHORIZATION_URL_LENGTH)
    private String authorizationUrl;

    /**
     * 对权限的描述
     */
    @NotBlank
    @Size(max = PermissionConstants.MAX_DESCRIPTION_LENGTH)
    private String description;

    /**
     * 权限是否可用
     */
    @NotNull
    private Boolean available;

}
