package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.UserRoleDTO;
import com.xiaohuashifu.recruit.user.service.do0.UserRoleDO;
import org.mapstruct.Mapper;

/**
 * 描述：UserRole 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface UserRoleAssembler {

    UserRoleDTO userRoleDOToUserRoleDTO(UserRoleDO userRoleDO);

}
