package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.user.api.constant.UserProfileConstants;
import com.xiaohuashifu.recruit.user.api.dto.UserProfileDTO;
import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;

import javax.validation.constraints.*;

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
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.User.NotExist: 用户不存在
     *              OperationConflict: 用户信息已经存在
     *
     * @param userId 用户编号
     * @return UserProfileDTO 创建的用户对象
     */
    Result<UserProfileDTO> createUserProfile(@NotNull(message = "The userId can't be null.")
                                             @Positive(message = "The userId must be greater than 0.") Long userId);

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的用户信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    Result<UserProfileDTO> getUserProfile(@NotNull(message = "The id can't be null.")
                                          @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的用户信息
     *
     * @param userId 用户主体编号
     * @return UserProfileDTO
     */
    Result<UserProfileDTO> getUserProfileByUserId(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId);


    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<UserProfileDTO> 带分页信息的查询结果，可能返回空列表
     */
    Result<PageInfo<UserProfileDTO>> listUserProfiles(@NotNull(message = "The query can't be null.")
                                                              UserProfileQuery query);

    /**
     * 通过用户个人信息编号获取用户编号
     *
     * @private 内部方法
     *
     * @param id 用户个人信息编号
     * @return 用户编号，若找不到返回 null
     */
    Long getUserId(Long id);

    /**
     * 更新姓名
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 用户个人信息不存在
     *
     * @param id 用户个人信息编号
     * @param newFullName 新姓名
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateFullName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newFullName can't be blank.")
            @FullName String newFullName);

    /**
     * 更新学号
     * 学号长度必须是12
     * 且为纯数字
     * 会进行学号格式校验
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 用户个人信息不存在
     *
     * @param id 用户个人信息编号
     * @param newStudentNumber 新学号
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateStudentNumber(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newStudentNumber can't be blank.")
            @StudentNumber String newStudentNumber);

    /**
     * 更新学院专业通过专业的编号
     * 学院专业必须是存在系统的学院专业库里的
     * 该方法会根据专业的编号去寻找学院
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 专业不存在 | 用户个人信息不存在
     *
     * @param id 用户个人信息编号
     * @param newMajorId 新专业编号
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateCollegeAndMajor(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The newMajorId can't be null.")
            @Positive(message = "The newMajorId must be greater than 0.") Long newMajorId);

    /**
     * 更新自我介绍
     *
     * @permission id 必须是用户本身
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 用户个人信息不存在
     *
     * @param id 用户个人信息编号
     * @param newIntroduction 新自我介绍
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateIntroduction(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newIntroduction can't be blank.")
            @Size(max = UserProfileConstants.MAX_INTRODUCTION_LENGTH,
                    message = "The length of newIntroduction must not be greater than "
                            + UserProfileConstants.MAX_INTRODUCTION_LENGTH + ".") String newIntroduction);

}
