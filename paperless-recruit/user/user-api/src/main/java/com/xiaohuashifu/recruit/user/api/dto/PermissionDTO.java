package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：权限传输对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO implements Serializable {

    /**
     * 权限编号
     */
    private Long id;

    /**
     * 父权限编号，若为0表示没有父亲
     */
    private Long parentPermissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 授权路径
     */
    private String authorizationUrl;

    /**
     * 对权限的描述
     */
    private String description;

    /**
     * 权限是否可用
     */
    private Boolean available;

}
