package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.UserProfileMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.UserProfileDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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

    private final Mapper mapper;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper, Mapper mapper) {
        this.userProfileMapper = userProfileMapper;
        this.mapper = mapper;
    }

    /**
     * 创建用户个人信息，创建的是一个空白的个人信息
     * 需要用户后续填写
     * 用户必须还没有创建个人信息
     *
     * @param userId 用户编号
     * @return 创建结果
     */
    @Override
    public Result<Void> createUserProfile(Long userId) {
        // 判断该编号的用户是否存在
        Result<Void> userExistsResult = userService.userExists(userId);
        if (!userExistsResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This user does not exist.");
        }

        // 判断用户信息是否已经存在
        int count = userProfileMapper.countByUserId(userId);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The user profile already exist.");
        }

        // 创建用户个人信息
        userProfileMapper.saveUserProfile(userId);
        return Result.success();
    }

    /**
     * 获取用户个人信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    @Override
    public Result<UserProfileDTO> getUserProfile(Long id) {
        UserProfileDO userProfileDO = userProfileMapper.getUserProfile(id);
        if (userProfileDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(userProfileDO, UserProfileDTO.class));
    }

    /**
     * 获取用户个人信息
     *
     * @param query 查询参数
     * @return PageInfo<UserProfileDTO> 带分页信息的查询结果
     */
    @Override
    public Result<PageInfo<UserProfileDTO>> getUserProfile(UserProfileQuery query) {
        List<UserProfileDO> userProfileDOList = userProfileMapper.getUserProfileByQuery(query);
        List<UserProfileDTO> userProfileDTOList = userProfileDOList
                .stream()
                .map(userProfileDO -> mapper.map(userProfileDO, UserProfileDTO.class))
                .collect(Collectors.toList());
        PageInfo<UserProfileDTO> pageInfo = new PageInfo<>(userProfileDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 通过用户编号获取用户个人信息
     *
     * @param userId 用户编号
     * @return UserProfileDTO
     */
    @Override
    public Result<UserProfileDTO> getUserProfileByUserId(Long userId) {
        UserProfileDO userProfileDO = userProfileMapper.getUserProfileByUserId(userId);
        if (userProfileDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(userProfileDO, UserProfileDTO.class));
    }

    /**
     * 更新姓名
     * 姓名长度必须在2-5之间
     *
     * @param userId 用户编号
     * @param fullName 姓名
     * @return 更新后的用户个人信息
     */
    @Override
    public Result<UserProfileDTO> updateFullName(Long userId, String fullName) {
        throw new UnsupportedOperationException();
    }

}
