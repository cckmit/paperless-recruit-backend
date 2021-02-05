package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：禁用角色的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisableRoleDTO implements Serializable {

    /**
     * 角色
     */
    private RoleDTO role;

    /**
     * 被禁用的子角色数量
     */
    private Integer disabledCount;

}
