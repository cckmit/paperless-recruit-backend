package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：报名表请求
 *
 * @author xhsf
 * @create 2021/2/9 19:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ApplicationFormRequest implements Serializable {

    /**
     * 头像地址
     */
    @Pattern(regexp = ApplicationFormConstants.AVATAR_URL_PATTERN)
    protected String avatarUrl;

    /**
     * 姓名
     */
    @NotAllCharactersBlank
    @FullName
    protected String fullName;

    /**
     * 手机号码
     */
    @NotAllCharactersBlank
    @Phone
    protected String phone;

    /**
     * 第一部门编号
     */
    @Positive
    protected Long firstDepartmentId;

    /**
     * 第二部门编号
     */
    @Positive
    protected Long secondDepartmentId;

    /**
     * 邮箱
     */
    @NotAllCharactersBlank
    @Email
    protected String email;

    /**
     * 个人简介
     */
    @NotAllCharactersBlank
    @Size(max = ApplicationFormConstants.MAX_INTRODUCTION_LENGTH)
    protected String introduction;

    /**
     * 附件
     */
    @Pattern(regexp = ApplicationFormConstants.ATTACHMENT_URL_PATTERN)
    protected String attachmentUrl;

    /**
     * 学号
     */
    @StudentNumber
    protected String studentNumber;

    /**
     * 学院编号
     */
    @Positive
    protected Long collegeId;

    /**
     * 专业编号
     */
    @Positive
    protected Long majorId;

    /**
     * 备注
     */
    @NotAllCharactersBlank
    @Size(max = ApplicationFormConstants.MAX_NOTE_LENGTH)
    protected String note;

}
