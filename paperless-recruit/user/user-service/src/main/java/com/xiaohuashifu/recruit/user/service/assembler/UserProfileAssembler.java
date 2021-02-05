package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.request.UpdateUserProfileRequest;
import com.xiaohuashifu.recruit.user.service.do0.UserProfileDO;
import org.mapstruct.Mapper;

/**
 * 描述：UserProfile 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface UserProfileAssembler {

    UserProfileDTO userProfileDOToUserProfileDTO(UserProfileDO userProfileDO);

    UserProfileDO updateUserProfileRequestToUserProfileDO(UpdateUserProfileRequest updateUserProfileRequest);

}
