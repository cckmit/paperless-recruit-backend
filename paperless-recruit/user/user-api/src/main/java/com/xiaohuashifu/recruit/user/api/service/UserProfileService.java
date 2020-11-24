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
 * @email: 827032783@qq.com
 * @create: 2020/11/23 17:01
 */
public interface UserProfileService {
    /**
     * 创建用户个人信息，创建的是一个空白的个人信息
     * 需要用户后续填写
     * 用户必须还没有创建个人信息
     *
     * @param userId 用户编号
     * @return 创建结果
     */
    default Result<Void> createUserProfile(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取用户个人信息
     *
     * @param id 用户个人信息编号
     * @return UserProfileDTO
     */
    default Result<UserProfileDTO> getUserProfile(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取用户个人信息
     *
     * @param query 查询参数
     * @return PageInfo<UserProfileDTO> 带分页信息的查询结果
     */
    default Result<PageInfo<UserProfileDTO>> getUserProfile(@NotNull UserProfileQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过用户编号获取用户个人信息
     *
     * @param userId 用户编号
     * @return UserProfileDTO
     */
    default Result<UserProfileDTO> getUserProfileByUserId(@NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新姓名
     * 姓名长度必须在2-5之间
     *
     * @param userId 用户编号
     * @param fullName 姓名
     * @return 更新后的用户个人信息
     */
    default Result<UserProfileDTO> updateFullName(
            @NotNull @Positive Long userId,
            @NotBlank @Size(min = 2, max = 5) @Chinese String fullName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新学号
     * 学号长度必须是12
     * 且为纯数字
     * 会进行学号格式校验
     *
     * @param userId 用户编号
     * @param studentNumber 学号
     * @return 更新后的用户个人信息
     */
    default Result<UserProfileDTO> updateStudentNumber(
            @NotNull @Positive Long userId,
            @NotBlank @Pattern(regexp = "^20\\d{10}$") String studentNumber) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新学院
     * 学院必须是存在系统的学院库里的
     *
     * @param userId 用户编号
     * @param college 学院
     * @return 更新后的用户个人信息
     */
    default Result<UserProfileDTO> updateCollege(
            @NotNull @Positive Long userId,
            @NotBlank @Size(min = 1, max = 50) String college) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新专业
     * 专业必须是存在系统的专业库里的
     *
     * @param userId 用户编号
     * @param major 专业
     * @return 更新后的用户个人信息
     */
    default Result<UserProfileDTO> updateMajor(
            @NotNull @Positive Long userId,
            @NotBlank @Size(min = 1, max = 50) String major) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新自我介绍
     *
     * @param userId 用户编号
     * @param introduction 自我介绍
     * @return 更新后的用户个人信息
     */
    default Result<UserProfileDTO> updateIntroduction(
            @NotNull @Positive Long userId,
            @NotBlank @Size(min = 1, max = 400) String introduction) {
        throw new UnsupportedOperationException();
    }

}
