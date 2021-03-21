package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
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
 * 描述：更新报名表请求
 *
 * @author xhsf
 * @create 2021/2/9 19:53
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateApplicationFormRequest implements Serializable {

    /**
     * 报名表编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 头像地址
     */
    @Pattern(regexp = ApplicationFormConstants.AVATAR_URL_PATTERN)
    private String avatarUrl;

    /**
     * 姓名
     */
    @NotAllCharactersBlank
    @FullName
    private String fullName;

    /**
     * 手机号码
     */
    @NotAllCharactersBlank
    @Phone
    private String phone;

    /**
     * 邮箱
     */
    @NotAllCharactersBlank
    @Email
    private String email;

    /**
     * 个人简介
     */
    @NotAllCharactersBlank
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
     * 学院
     */
    @NotAllCharactersBlank
    @Size(max = ApplicationFormConstants.MAX_COLLEGE_LENGTH)
    private String college;

    /**
     * 专业
     */
    @NotAllCharactersBlank
    @Size(max = ApplicationFormConstants.MAX_MAJOR_LENGTH)
    private String major;

    /**
     * 备注
     */
    @NotAllCharactersBlank
    @Size(max = ApplicationFormConstants.MAX_NOTE_LENGTH)
    private String note;

}
