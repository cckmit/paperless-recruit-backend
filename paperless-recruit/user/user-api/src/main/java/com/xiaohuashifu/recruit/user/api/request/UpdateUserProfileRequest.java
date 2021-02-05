package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.user.api.constant.UserProfileConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：更新用户信息请求
 *
 * @author xhsf
 * @create 2021/2/6 01:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequest {

    /**
     * 用户信息编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 姓名
     */
    @FullName
    private String fullName;

    /**
     * 学号
     */
    @StudentNumber
    private String studentNumber;

    /**
     * 专业编号
     */
    @Positive
    private Long majorId;

    /**
     * 简介
     */
    @Size(max = UserProfileConstants.MAX_INTRODUCTION_LENGTH)
    private String introduction;

}
