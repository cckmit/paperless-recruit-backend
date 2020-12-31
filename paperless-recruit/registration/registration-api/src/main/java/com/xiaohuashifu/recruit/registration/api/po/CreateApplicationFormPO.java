package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
@SuperBuilder
public class CreateApplicationFormPO implements Serializable {

    /**
     * 报名者用户编号
     */
    @NotNull(message = "The userId can't be null.")
    @Positive(message = "The userId must be greater than 0.")
    private Long userId;

    /**
     * 招新编号
     */
    @NotNull(message = "The recruitmentId can't be null.")
    @Positive(message = "The recruitmentId must be greater than 0.")
    private Long recruitmentId;

    /**
     * 头像
     */
    private ApplicationFormAvatarPO avatar;

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
    @Positive(message = "The firstDepartmentId must be greater than 0.")
    private Long firstDepartmentId;

    /**
     * 第二部门
     */
    @Positive(message = "The secondDepartmentId must be greater than 0.")
    private Long secondDepartmentId;

    /**
     * 邮箱
     */
    @Email
    private String email;

    /**
     * 个人简介
     */
    @Size(max = ApplicationFormConstants.MAX_INTRODUCTION_LENGTH,
            message = "The length of introduction must not be greater than "
                    + ApplicationFormConstants.MAX_INTRODUCTION_LENGTH + ".")
    private String introduction;

    /**
     * 附件
     */
    private ApplicationFormAttachmentPO attachment;

    /**
     * 学号
     */
    @StudentNumber
    private String studentNumber;

    /**
     * 学院编号
     */
    @Positive(message = "The collegeId must be greater than 0.")
    private Long collegeId;

    /**
     * 专业编号
     */
    @Positive(message = "The majorId must be greater than 0.")
    private Long majorId;

    /**
     * 备注
     */
    @Size(max = ApplicationFormConstants.MAX_NOTE_LENGTH,
            message = "The length of note must not be greater than " + ApplicationFormConstants.MAX_NOTE_LENGTH + ".")
    private String note;

}
