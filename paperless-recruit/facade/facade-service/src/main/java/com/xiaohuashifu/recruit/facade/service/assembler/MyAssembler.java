package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.MyVO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：My 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper
public interface MyAssembler {

    MyAssembler INSTANCE = Mappers.getMapper(MyAssembler.class);

    MyVO userProfileDTO2MyVO(UserProfileDTO userProfileDTO);

}
