package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.RolePermissionDTO;
import com.xiaohuashifu.recruit.user.service.do0.RolePermissionDO;
import org.mapstruct.Mapper;

/**
 * 描述：RolePermission 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface RolePermissionAssembler {

    RolePermissionDTO rolePermissionDOToRolePermissionDTO(RolePermissionDO rolePermissionDO);

}
