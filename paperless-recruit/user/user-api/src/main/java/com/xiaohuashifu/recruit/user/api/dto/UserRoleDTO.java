package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：用户角色DTO
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Long id;
    private Long userId;
    private Long roleId;
}
