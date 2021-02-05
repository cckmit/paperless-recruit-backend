package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：禁用权限的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisablePermissionDTO implements Serializable {

    /**
     * 权限
     */
    private PermissionDTO permission;

    /**
     * 被禁用的子权限数量
     */
    private Integer disabledCount;

}
