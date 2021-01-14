package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.UserVO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：User 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper
public interface UserAssembler {

    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);

    UserVO userDTO2UserVO(UserDTO userDTO);

}
