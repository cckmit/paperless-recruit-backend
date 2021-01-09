package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.MyAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.CollegeManager;
import com.xiaohuashifu.recruit.facade.service.manager.MajorManager;
import com.xiaohuashifu.recruit.facade.service.manager.MyManager;
import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;
import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;
import com.xiaohuashifu.recruit.facade.service.vo.MyVO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 描述：我的页面管理器
 *
 * @author xhsf
 * @create 2021/1/9 12:46
 */
@Component
public class MyManagerImpl implements MyManager {

    private final MyAssembler myAssembler = MyAssembler.INSTANCE;

    @Reference
    private UserProfileService userProfileService;

    private final MajorManager majorManager;

    private final CollegeManager collegeManager;

    public MyManagerImpl(MajorManager majorManager, CollegeManager collegeManager) {
        this.majorManager = majorManager;
        this.collegeManager = collegeManager;
    }

    /**
     * 获取我的页面
     *
     * @param userId 用户编号
     * @return MyVO
     */
    @Override
    @Cacheable(cacheNames = "default", key = "'my:' + #userId")
    public MyVO getPage(Long userId) {
        Result<UserProfileDTO> getUserProfileResult = userProfileService.getUserProfileByUserId(userId);
        UserProfileDTO userProfileDTO = getUserProfileResult.getData();
        if (userProfileDTO == null) {
            return new MyVO();
        }

        MyVO myVO = myAssembler.userProfileDTO2MyVO(userProfileDTO);
        MajorVO majorVO = majorManager.getMajor(userProfileDTO.getMajorId());
        myVO.setMajor(majorVO.getMajorName());
        CollegeVO collegeVO = collegeManager.getCollege(userProfileDTO.getCollegeId());
        myVO.setCollege(collegeVO.getCollegeName());
        return myVO;
    }
}
