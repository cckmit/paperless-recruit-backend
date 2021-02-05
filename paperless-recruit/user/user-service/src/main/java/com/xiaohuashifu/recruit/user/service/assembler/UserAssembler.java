package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.service.do0.UserDO;
import org.mapstruct.Mapper;

/**
 * 描述：User 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface UserAssembler {

    UserDTO userDOToUserDTO(UserDO userDO);

}
