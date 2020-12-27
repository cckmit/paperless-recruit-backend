package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;

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
    private Set<Long> recruitmentCollegeIds;

    /**
     * 招新专业列表，空表示不限
     */
    @NotNull(message = "The recruitmentMajorIds can't be null.")
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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getRecruitmentNumbers() {
        return recruitmentNumbers;
    }

    public void setRecruitmentNumbers(String recruitmentNumbers) {
        this.recruitmentNumbers = recruitmentNumbers;
    }

    public String getPositionDuty() {
        return positionDuty;
    }

    public void setPositionDuty(String positionDuty) {
        this.positionDuty = positionDuty;
    }

    public String getPositionRequirement() {
        return positionRequirement;
    }

    public void setPositionRequirement(String positionRequirement) {
        this.positionRequirement = positionRequirement;
    }

    public Set<GradeEnum> getRecruitmentGrades() {
        return recruitmentGrades;
    }

    public void setRecruitmentGrades(Set<GradeEnum> recruitmentGrades) {
        this.recruitmentGrades = recruitmentGrades;
    }

    public Set<Long> getRecruitmentDepartmentIds() {
        return recruitmentDepartmentIds;
    }

    public void setRecruitmentDepartmentIds(Set<Long> recruitmentDepartmentIds) {
        this.recruitmentDepartmentIds = recruitmentDepartmentIds;
    }

    public Set<Long> getRecruitmentCollegeIds() {
        return recruitmentCollegeIds;
    }

    public void setRecruitmentCollegeIds(Set<Long> recruitmentCollegeIds) {
        this.recruitmentCollegeIds = recruitmentCollegeIds;
    }

    public Set<Long> getRecruitmentMajorIds() {
        return recruitmentMajorIds;
    }

    public void setRecruitmentMajorIds(Set<Long> recruitmentMajorIds) {
        this.recruitmentMajorIds = recruitmentMajorIds;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalDateTime getRegistrationTimeFrom() {
        return registrationTimeFrom;
    }

    public void setRegistrationTimeFrom(LocalDateTime registrationTimeFrom) {
        this.registrationTimeFrom = registrationTimeFrom;
    }

    public LocalDateTime getRegistrationTimeTo() {
        return registrationTimeTo;
    }

    public void setRegistrationTimeTo(LocalDateTime registrationTimeTo) {
        this.registrationTimeTo = registrationTimeTo;
    }

    @Override
    public String toString() {
        return "CreateRecruitmentPO{" +
                "organizationId=" + organizationId +
                ", positionName='" + positionName + '\'' +
                ", recruitmentNumbers='" + recruitmentNumbers + '\'' +
                ", positionDuty='" + positionDuty + '\'' +
                ", positionRequirement='" + positionRequirement + '\'' +
                ", recruitmentGrades=" + recruitmentGrades +
                ", recruitmentDepartmentIds=" + recruitmentDepartmentIds +
                ", recruitmentCollegeIds=" + recruitmentCollegeIds +
                ", recruitmentMajorIds=" + recruitmentMajorIds +
                ", releaseTime=" + releaseTime +
                ", registrationTimeFrom=" + registrationTimeFrom +
                ", registrationTimeTo=" + registrationTimeTo +
                '}';
    }

    public static final class Builder {
        private Long organizationId;
        private String positionName;
        private String recruitmentNumbers;
        private String positionDuty;
        private String positionRequirement;
        private Set<GradeEnum> recruitmentGrades;
        private Set<Long> recruitmentDepartmentIds;
        private Set<Long> recruitmentCollegeIds;
        private Set<Long> recruitmentMajorIds;
        private LocalDateTime releaseTime;
        private LocalDateTime registrationTimeFrom;
        private LocalDateTime registrationTimeTo;

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public Builder recruitmentNumbers(String recruitmentNumbers) {
            this.recruitmentNumbers = recruitmentNumbers;
            return this;
        }

        public Builder positionDuty(String positionDuty) {
            this.positionDuty = positionDuty;
            return this;
        }

        public Builder positionRequirement(String positionRequirement) {
            this.positionRequirement = positionRequirement;
            return this;
        }

        public Builder recruitmentGrades(Set<GradeEnum> recruitmentGrades) {
            this.recruitmentGrades = recruitmentGrades;
            return this;
        }

        public Builder recruitmentDepartmentIds(Set<Long> recruitmentDepartmentIds) {
            this.recruitmentDepartmentIds = recruitmentDepartmentIds;
            return this;
        }

        public Builder recruitmentCollegeIds(Set<Long> recruitmentCollegeIds) {
            this.recruitmentCollegeIds = recruitmentCollegeIds;
            return this;
        }

        public Builder recruitmentMajorIds(Set<Long> recruitmentMajorIds) {
            this.recruitmentMajorIds = recruitmentMajorIds;
            return this;
        }

        public Builder releaseTime(LocalDateTime releaseTime) {
            this.releaseTime = releaseTime;
            return this;
        }

        public Builder registrationTimeFrom(LocalDateTime registrationTimeFrom) {
            this.registrationTimeFrom = registrationTimeFrom;
            return this;
        }

        public Builder registrationTimeTo(LocalDateTime registrationTimeTo) {
            this.registrationTimeTo = registrationTimeTo;
            return this;
        }

        public CreateRecruitmentPO build() {
            CreateRecruitmentPO createRecruitmentPO = new CreateRecruitmentPO();
            createRecruitmentPO.setOrganizationId(organizationId);
            createRecruitmentPO.setPositionName(positionName);
            createRecruitmentPO.setRecruitmentNumbers(recruitmentNumbers);
            createRecruitmentPO.setPositionDuty(positionDuty);
            createRecruitmentPO.setPositionRequirement(positionRequirement);
            createRecruitmentPO.setRecruitmentGrades(recruitmentGrades);
            createRecruitmentPO.setRecruitmentDepartmentIds(recruitmentDepartmentIds);
            createRecruitmentPO.setRecruitmentCollegeIds(recruitmentCollegeIds);
            createRecruitmentPO.setRecruitmentMajorIds(recruitmentMajorIds);
            createRecruitmentPO.setReleaseTime(releaseTime);
            createRecruitmentPO.setRegistrationTimeFrom(registrationTimeFrom);
            createRecruitmentPO.setRegistrationTimeTo(registrationTimeTo);
            return createRecruitmentPO;
        }
    }
}
