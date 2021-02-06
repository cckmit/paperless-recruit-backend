package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：角色权限DTO
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDTO {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
