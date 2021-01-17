package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.UserProfileVO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import org.mapstruct.Mapper;

/**
 * 描述：用户信息装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface UserProfileAssembler {

    UserProfileVO userProfileDTOToUserProfileVO(UserProfileDTO userProfileDTO);

}
