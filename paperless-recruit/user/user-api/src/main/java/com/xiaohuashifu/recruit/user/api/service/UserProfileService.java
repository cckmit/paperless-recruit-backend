package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.user.api.constant.UserProfileConstants;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.request.UpdateUserProfileRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：用户个人信息服务
 *
 * @author: xhsf
 * @create: 2020/11/23 17:01
 */
public interface UserProfileService {

    /**
     * 创建用户个人信息，创建的是一个空白的个人信息
     * 需要用户后续填写
     * 用户必须还没有创建个人信息
     *
     * @private 内部方法
     *
     * @param userId 用户编号
     * @return UserProfileDTO 创建的用户对象
     */
    UserProfileDTO createUserProfile(@NotNull @Positive Long userId) throws ServiceException;

    /**
     * 获取用户个人信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    UserProfileDTO getUserProfile(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取用户个人信息
     *
     * @param userId 用户主体编号
     * @return UserProfileDTO
     */
    UserProfileDTO getUserProfileByUserId(@NotNull @Positive Long userId) throws NotFoundServiceException;


    /**
     * 获取用户个人信息
     *
     * @param query 查询参数
     * @return QueryResult<UserProfileDTO> 带分页信息的查询结果，可能返回空列表
     */
    QueryResult<UserProfileDTO> listUserProfiles(@NotNull UserProfileQuery query);

    /**
     * 更新用户信息
     *
     * 注：更新学院专业时通过专业的编号
     *  学院专业必须是存在系统的学院专业库里的
     *  该方法会根据专业的编号去寻找学院
     *
     * @param request UpdateUserProfileRequest
     * @return 更新后的用户个人信息
     */
    UserProfileDTO updateUserProfile(@NotNull UpdateUserProfileRequest request) throws ServiceException;

}
