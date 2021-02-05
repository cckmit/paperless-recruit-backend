package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：角色传输对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {

    /**
     * 角色编号
     */
    private Long id;

    /**
     * 父角色编号，若为0表示没有父亲
     */
    private Long parentRoleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 对角色的描述
     */
    private String description;

    /**
     * 角色是否可用
     */
    private Boolean available;

}
