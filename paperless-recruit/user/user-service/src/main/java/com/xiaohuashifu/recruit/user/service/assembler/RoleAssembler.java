package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.request.CreateRoleRequest;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;
import org.mapstruct.Mapper;

/**
 * 描述：Role 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface RoleAssembler {

    RoleDTO roleDOToRoleDTO(RoleDO roleDO);

    RoleDO createRoleRequestToRoleDO(CreateRoleRequest createRoleRequest);

}
