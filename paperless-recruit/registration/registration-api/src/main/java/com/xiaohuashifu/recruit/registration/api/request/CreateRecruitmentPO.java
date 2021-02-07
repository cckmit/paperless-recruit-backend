package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：创建招新的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class CreateRecruitmentPO implements Serializable {

    /**
     * 招新的组织编号
     */
    @NotNull(message = "The organizationId can't be null.")
    @Positive(message = "The organizationId must be greater than 0.")
    private Long organizationId;

    /**
     * 招新的职位名
     */
    @NotBlank(message = "The positionName can't be blank.")
    @Size(max = RecruitmentConstants.MAX_POSITION_NAME_LENGTH,
            message = "The length of positionName must not be greater than "
                    + RecruitmentConstants.MAX_POSITION_NAME_LENGTH + ".")
    private String positionName;

    /**
     * 招新人数
     */
    @NotBlank(message = "The recruitmentNumbers can't be blank.")
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_NUMBERS_LENGTH,
            message = "The length of recruitmentNumbers must not be greater than "
                    + RecruitmentConstants.MAX_RECRUITMENT_NUMBERS_LENGTH + ".")
    private String recruitmentNumbers;

    /**
     * 职位职责
     */
    @NotBlank(message = "The positionDuty can't be blank.")
    @Size(max = RecruitmentConstants.MAX_POSITION_DUTY_LENGTH,
            message = "The length of positionDuty must not be greater than "
                    + RecruitmentConstants.MAX_POSITION_DUTY_LENGTH + ".")
    private String positionDuty;

    /**
     * 职位要求
     */
    @NotBlank(message = "The positionRequirement can't be blank.")
    @Size(max = RecruitmentConstants.MAX_POSITION_REQUIREMENT_LENGTH,
            message = "The length of positionRequirement must not be greater than "
                    + RecruitmentConstants.MAX_POSITION_REQUIREMENT_LENGTH + ".")
    private String positionRequirement;

    /**
     * 招新年级，空表示不限
     */
    @NotNull(message = "The recruitmentGrades can't be null.")
    private Set<GradeEnum> recruitmentGrades;

    /**
     * 招新部门编号列表，空表示不限
     */
    @NotNull(message = "The recruitmentDepartmentIds can't be null.")
    private Set<Long> recruitmentDepartmentIds;

    /**
     * 招新学院列表，空表示不限
     */
    @NotNull(message = "The recruitmentCollegeIds can't be null.")
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS,
            message = "The length of recruitmentCollegeIds must not be greater than "
                    + RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS + ".")
    private Set<Long> recruitmentCollegeIds;

    /**
     * 招新专业列表，空表示不限
     */
    @NotNull(message = "The recruitmentMajorIds can't be null.")
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS,
            message = "The length of recruitmentMajorIds must not be greater than "
                    + RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS + ".")
    private Set<Long> recruitmentMajorIds;

    /**
     * 最大9999-12-31 23:59:59
     * 招新发布时间，空表示立刻发布，必须大于等于当前时间
     */
    @FutureOrPresent(message = "The releaseTime must be greater than the current time.")
    private LocalDateTime releaseTime;

    /**
     * 最大9999-12-31 23:59:59
     * 报名开始时间，空表示立刻报名，必须大于等于发布时间
     */
    private LocalDateTime registrationTimeFrom;

    /**
     * 最大9999-12-31 23:59:59
     * 报名结束时间，空表示报名时间无限长（其实是9999-12-31 23:59:59），必须大于报名开始时间
     */
    private LocalDateTime registrationTimeTo;

}
