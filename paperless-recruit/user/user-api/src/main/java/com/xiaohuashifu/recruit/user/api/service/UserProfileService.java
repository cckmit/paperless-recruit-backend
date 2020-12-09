package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Chinese;
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
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 用户信息已经存在
     *
     * @param userId 用户编号
     * @return UserProfileDTO 创建的用户对象
     */
    Result<UserProfileDTO> createUserProfile(@NotNull @Positive Long userId);

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的用户信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    Result<UserProfileDTO> getUserProfile(@NotNull @Positive Long id);

    /**
     * 获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<UserProfileDTO> 带分页信息的查询结果，可能返回空列表
     */
    Result<PageInfo<UserProfileDTO>> listUserProfiles(@NotNull UserProfileQuery query);

    /**
     * 通过用户编号获取用户个人信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该用户编号的用户信息
     *
     * @param userId 用户编号
     * @return UserProfileDTO
     */
    Result<UserProfileDTO> getUserProfileByUserId(@NotNull @Positive Long userId);

    /**
     * 更新姓名
     * 姓名长度必须在2-5之间
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户信息不存在
     *
     * @param userId 用户编号
     * @param newFullName 新姓名
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateFullName(@NotNull @Positive Long userId,
                                          @NotBlank @Size(min = 2, max = 5) @Chinese String newFullName);

    /**
     * 更新学号
     * 学号长度必须是12
     * 且为纯数字
     * 会进行学号格式校验
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户信息不存在
     *
     * @param userId 用户编号
     * @param newStudentNumber 新学号
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateStudentNumber(@NotNull @Positive Long userId,
                                               @NotBlank @Pattern(regexp = "^20\\d{10}$") String newStudentNumber);

    /**
     * 更新学院专业通过专业的编号
     * 学院专业必须是存在系统的学院专业库里的
     * 该方法会根据专业的编号去寻找学院
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户信息不存在 | 专业不存在
     *
     * @param userId 用户编号
     * @param newMajorId 新专业编号
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateCollegeAndMajorByMajorId(@NotNull @Positive Long userId,
                                                          @NotNull @Positive Long newMajorId);

    /**
     * 更新自我介绍
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户信息不存在
     *
     * @param userId 用户编号
     * @param newIntroduction 新自我介绍
     * @return 更新后的用户个人信息
     */
    Result<UserProfileDTO> updateIntroduction(@NotNull @Positive Long userId,
                                              @NotBlank @Size(min = 1, max = 400) String newIntroduction);

}
