package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.UserProfileAssembler;
import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
import com.xiaohuashifu.recruit.facade.service.manager.CollegeManager;
import com.xiaohuashifu.recruit.facade.service.manager.MajorManager;
import com.xiaohuashifu.recruit.facade.service.manager.UserProfileManager;
import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;
import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;
import com.xiaohuashifu.recruit.facade.service.vo.UserProfileVO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 描述：用户信息管理器
 *
 * @author xhsf
 * @create 2021/1/9 12:46
 */
@Component
public class UserProfileManagerImpl implements UserProfileManager {

    private final UserProfileAssembler userProfileAssembler;

    @Reference
    private UserProfileService userProfileService;

    private final MajorManager majorManager;

    private final CollegeManager collegeManager;

    public UserProfileManagerImpl(UserProfileAssembler userProfileAssembler, MajorManager majorManager,
                                  CollegeManager collegeManager) {
        this.userProfileAssembler = userProfileAssembler;
        this.majorManager = majorManager;
        this.collegeManager = collegeManager;
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户编号
     * @return UserProfileVO
     */
    @Override
    @Cacheable(cacheNames = "default", key = "'users:' + #userId + ':profiles'")
    public UserProfileVO getUserProfile(Long userId) {
        Result<UserProfileDTO> getUserProfileResult = userProfileService.getUserProfileByUserId(userId);
        UserProfileDTO userProfileDTO = getUserProfileResult.getData();
        if (userProfileDTO == null) {
            throw new ResponseEntityException(getUserProfileResult);
        }

        UserProfileVO userProfileVO = userProfileAssembler.userProfileDTOToUserProfileVO(userProfileDTO);
        MajorVO majorVO = majorManager.getMajor(userProfileDTO.getMajorId());
        userProfileVO.setMajor(majorVO.getMajorName());
        CollegeVO collegeVO = collegeManager.getCollege(userProfileDTO.getCollegeId());
        userProfileVO.setCollege(collegeVO.getCollegeName());
        return userProfileVO;
    }
}
