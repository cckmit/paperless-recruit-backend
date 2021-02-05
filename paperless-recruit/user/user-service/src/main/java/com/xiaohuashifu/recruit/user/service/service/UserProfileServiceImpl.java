package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.request.UpdateUserProfileRequest;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.assembler.UserProfileAssembler;
import com.xiaohuashifu.recruit.user.service.dao.UserProfileMapper;
import com.xiaohuashifu.recruit.user.service.do0.UserProfileDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：用户个人信息服务
 *
 * @author xhsf
 * @create 2020/11/24 19:38
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;

    private final UserProfileAssembler userProfileAssembler;

    @Reference
    private UserService userService;

    @Reference
    private MajorService majorService;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper, UserProfileAssembler userProfileAssembler) {
        this.userProfileMapper = userProfileMapper;
        this.userProfileAssembler = userProfileAssembler;
    }

    @Override
    public UserProfileDTO createUserProfile(Long userId) {
        // 判断该编号的用户是否存在
        userService.getUser(userId);

        // 判断用户信息是否已经存在
        UserProfileDO userProfileDO = userProfileMapper.selectByUserId(userId);
        if (userProfileDO != null) {
            throw new DuplicateServiceException("The user profile already exist.");
        }

        // 创建用户个人信息
        UserProfileDO userProfileDOForInsert = UserProfileDO.builder().userId(userId).build();
        userProfileMapper.insert(userProfileDOForInsert);
        return getUserProfile(userProfileDOForInsert.getId());
    }

    @Override
    public UserProfileDTO getUserProfile(Long id) {
        UserProfileDO userProfileDO = userProfileMapper.selectById(id);
        if (userProfileDO == null) {
            throw new NotFoundServiceException("userProfile", "id", id);
        }
        return userProfileAssembler.userProfileDOToUserProfileDTO(userProfileDO);
    }

    @Override
    public UserProfileDTO getUserProfileByUserId(Long userId) {
        UserProfileDO userProfileDO = userProfileMapper.selectByUserId(userId);
        if (userProfileDO == null) {
            throw new NotFoundServiceException("userProfile", "userId", userId);
        }
        return userProfileAssembler.userProfileDOToUserProfileDTO(userProfileDO);
    }

    @Override
    public QueryResult<UserProfileDTO> listUserProfiles(UserProfileQuery query) {
        LambdaQueryWrapper<UserProfileDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getUserId() != null, UserProfileDO::getUserId, query.getUserId())
                .likeRight(query.getFullName() != null, UserProfileDO::getFullName, query.getFullName())
                .likeRight(query.getStudentNumber() != null, UserProfileDO::getStudentNumber,
                        query.getStudentNumber())
                .eq(query.getCollegeId() != null, UserProfileDO::getCollegeId, query.getCollegeId())
                .eq(query.getMajorId() != null, UserProfileDO::getMajorId, query.getMajorId());

        Page<UserProfileDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        userProfileMapper.selectPage(page, wrapper);
        List<UserProfileDTO> userProfileDTOS = page.getRecords()
                .stream().map(userProfileAssembler::userProfileDOToUserProfileDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), userProfileDTOS);
    }

    @Override
    public UserProfileDTO updateUserProfile(UpdateUserProfileRequest request) {
        UserProfileDO userProfileDO = userProfileAssembler.updateUserProfileRequestToUserProfileDO(request);
        if (userProfileDO.getMajorId() != null) {
            MajorDTO majorDTO = majorService.getMajor(userProfileDO.getMajorId());
            userProfileDO.setCollegeId(majorDTO.getCollegeId());
        }

        // 更新用户简介
        userProfileMapper.updateById(userProfileDO);
        return getUserProfile(request.getId());
    }

}
