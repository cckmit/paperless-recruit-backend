package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建报名表的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateApplicationFormRequest implements Serializable {

    /**
     * 报名者用户编号
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * 招新编号
     */
    @NotNull
    @Positive
    private Long recruitmentId;

    /**
     * 头像
     */
    @Pattern(regexp = ApplicationFormConstants.AVATAR_URL_PATTERN)
    private String avatarUrl;

    /**
     * 姓名
     */
    @FullName
    private String fullName;

    /**
     * 手机号码
     */
    @Phone
    private String phone;

    /**
     * 第一部门
     */
    @Positive
    private Long firstDepartmentId;

    /**
     * 第二部门
     */
    @Positive
    private Long secondDepartmentId;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 个人简介
     */
    @Size(max = ApplicationFormConstants.MAX_INTRODUCTION_LENGTH)
    private String introduction;

    /**
     * 附件
     */
    @Pattern(regexp = ApplicationFormConstants.ATTACHMENT_URL_PATTERN)
    private String attachmentUrl;

    /**
     * 学号
     */
    @StudentNumber
    private String studentNumber;

    /**
     * 学院编号
     */
    @Positive
    private Long collegeId;

    /**
     * 专业编号
     */
    @Positive
    private Long majorId;

    /**
     * 备注
     */
    @Size(max = ApplicationFormConstants.MAX_NOTE_LENGTH)
    private String note;

}
