package com.xiaohuashifu.recruit.user.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
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

    @Reference
    private UserService userService;

    @Reference
    private CollegeService collegeService;

    @Reference
    private MajorService majorService;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * 创建用户个人信息，创建的是一个空白的个人信息
     * 需要用户后续填写
     * 用户必须还没有创建个人信息
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.User.NotExist: 用户不存在
     *              OperationConflict: 用户信息已经存在
     *
     * @param userId 用户编号
     * @return UserProfileDTO 创建的用户对象
     */
    @Override
    public Result<UserProfileDTO> createUserProfile(Long userId) {
        // 判断该编号的用户是否存在
        Result<Void> userExistsResult = userService.userExists(userId);
        if (!userExistsResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_USER_NOT_EXIST, "The user does not exist.");
        }

        // 判断用户信息是否已经存在
        int count = userProfileMapper.countByUserId(userId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The user profile already exist.");
        }

        // 创建用户个人信息
        UserProfileDO userProfileDO = new UserProfileDO.Builder().userId(userId).build();
        userProfileMapper.insertUserProfile(userProfileDO);
        return getUserProfile(userProfileDO.getId());
    }

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的用户信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    @Override
    public Result<UserProfileDTO> getUserProfile(Long id) {
        UserProfileDO userProfileDO = userProfileMapper.getUserProfile(id);
        if (userProfileDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(userProfileDO2UserProfileDTO(userProfileDO));
    }

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<UserProfileDTO> 带分页信息的查询结果，可能返回空列表
     */
    @Override
    public Result<PageInfo<UserProfileDTO>> listUserProfiles(UserProfileQuery query) {
        List<UserProfileDO> userProfileDOList = userProfileMapper.listUserProfiles(query);
        List<UserProfileDTO> userProfileDTOList = userProfileDOList
                .stream()
                .map(this::userProfileDO2UserProfileDTO)
                .collect(Collectors.toList());
        PageInfo<UserProfileDTO> pageInfo = new PageInfo<>(userProfileDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 通过用户个人信息编号获取用户编号
     *
     * @private 内部方法
     *
     * @param id 用户个人信息编号
     * @return 用户编号，若找不到返回 null
     */
    @Override
    public Long getUserId(Long id) {
        return userProfileMapper.getUserId(id);
    }

    /**
     * 更新姓名
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param id 用户个人信息编号
     * @param newFullName 新姓名
     * @return 更新后的用户个人信息
     */
    @Override
    public Result<UserProfileDTO> updateFullName(Long id, String newFullName) {
        // 更新姓名
        userProfileMapper.updateFullName(id, newFullName);
        return getUserProfile(id);
    }

    /**
     * 更新学号
     * 学号长度必须是12
     * 且为纯数字
     * 会进行学号格式校验
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param id 用户个人信息编号
     * @param newStudentNumber 新学号
     * @return 更新后的用户个人信息
     */
    @Override
    public Result<UserProfileDTO> updateStudentNumber(Long id, String newStudentNumber) {
        // 更新学号
        userProfileMapper.updateStudentNumber(id, newStudentNumber);
        return getUserProfile(id);
    }

    /**
     * 更新学院专业通过专业的编号
     * 学院专业必须是存在系统的学院专业库里的
     * 该方法会根据专业的编号去寻找学院
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 专业不存在
     *
     * @param id 用户个人信息编号
     * @param newMajorId 新专业编号
     * @return 更新后的用户个人信息
     */
    @Override
    public Result<UserProfileDTO> updateCollegeAndMajor(Long id, Long newMajorId) {
        // 判断该专业编号是否存在系统库里
        Result<MajorDTO> getMajorResult = majorService.getMajor(newMajorId);
        if (!getMajorResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The major does not exist.");
        }

        // 更新学院专业
        MajorDTO majorDTO = getMajorResult.getData();
        userProfileMapper.updateCollegeIdAndMajorId(id, majorDTO.getCollegeId(), majorDTO.getId());
        return getUserProfile(id);
    }

    /**
     * 更新自我介绍
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param id 用户个人信息编号
     * @param newIntroduction 新自我介绍
     * @return 更新后的用户个人信息
     */
    @Override
    public Result<UserProfileDTO> updateIntroduction(Long id, String newIntroduction) {
        // 更新自我介绍
        userProfileMapper.updateIntroduction(id, newIntroduction);
        return getUserProfile(id);
    }

    /**
     * UserProfileDO to UserProfileDTO
     *
     * @param userProfileDO UserProfileDO
     * @return UserProfileDTO
     */
    private UserProfileDTO userProfileDO2UserProfileDTO(UserProfileDO userProfileDO) {
        return new UserProfileDTO.Builder()
                .id(userProfileDO.getId())
                .userId(userProfileDO.getUserId())
                .fullName(userProfileDO.getFullName())
                .studentNumber(userProfileDO.getStudentNumber())
                .collegeId(userProfileDO.getCollegeId())
                .majorId(userProfileDO.getMajorId())
                .introduction(userProfileDO.getIntroduction())
                .build();
    }

}
