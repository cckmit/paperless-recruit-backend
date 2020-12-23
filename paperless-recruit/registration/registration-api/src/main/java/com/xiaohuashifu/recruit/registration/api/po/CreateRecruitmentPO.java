package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long organizationId;

    /**
     * 招新的职位名
     */
    private String positionName;

    /**
     * 招新人数
     */
    private String recruitmentNumbers;

    /**
     * 职位职责
     */
    private String positionDuty;

    /**
     * 职位要求
     */
    private String positionRequirement;

    /**
     * 招新年级
     */
    private List<GradeEnum> recruitmentGrades;

    /**
     * 招新部门编号列表
     */
    private List<Long> recruitmentDepartmentIds;

    /**
     * 招新学院列表，可以指定特殊值 “不限”
     */
    private List<String> recruitmentColleges;

    /**
     * 招新专业列表，可以指定特殊值 “不限”
     */
    private List<String> recruitmentMajors;

    /**
     * 招新发布时间
     */
    private LocalDateTime releaseTime;

    /**
     * 报名开始时间
     */
    private LocalDateTime registrationTimeFrom;

    /**
     * 报名结束时间
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

    public List<GradeEnum> getRecruitmentGrades() {
        return recruitmentGrades;
    }

    public void setRecruitmentGrades(List<GradeEnum> recruitmentGrades) {
        this.recruitmentGrades = recruitmentGrades;
    }

    public List<Long> getRecruitmentDepartmentIds() {
        return recruitmentDepartmentIds;
    }

    public void setRecruitmentDepartmentIds(List<Long> recruitmentDepartmentIds) {
        this.recruitmentDepartmentIds = recruitmentDepartmentIds;
    }

    public List<String> getRecruitmentColleges() {
        return recruitmentColleges;
    }

    public void setRecruitmentColleges(List<String> recruitmentColleges) {
        this.recruitmentColleges = recruitmentColleges;
    }

    public List<String> getRecruitmentMajors() {
        return recruitmentMajors;
    }

    public void setRecruitmentMajors(List<String> recruitmentMajors) {
        this.recruitmentMajors = recruitmentMajors;
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
                ", recruitmentColleges=" + recruitmentColleges +
                ", recruitmentMajors=" + recruitmentMajors +
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
        private List<GradeEnum> recruitmentGrades;
        private List<Long> recruitmentDepartmentIds;
        private List<String> recruitmentColleges;
        private List<String> recruitmentMajors;
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

        public Builder recruitmentGrades(List<GradeEnum> recruitmentGrades) {
            this.recruitmentGrades = recruitmentGrades;
            return this;
        }

        public Builder recruitmentDepartmentIds(List<Long> recruitmentDepartmentIds) {
            this.recruitmentDepartmentIds = recruitmentDepartmentIds;
            return this;
        }

        public Builder recruitmentColleges(List<String> recruitmentColleges) {
            this.recruitmentColleges = recruitmentColleges;
            return this;
        }

        public Builder recruitmentMajors(List<String> recruitmentMajors) {
            this.recruitmentMajors = recruitmentMajors;
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
            createRecruitmentPO.setRecruitmentColleges(recruitmentColleges);
            createRecruitmentPO.setRecruitmentMajors(recruitmentMajors);
            createRecruitmentPO.setReleaseTime(releaseTime);
            createRecruitmentPO.setRegistrationTimeFrom(registrationTimeFrom);
            createRecruitmentPO.setRegistrationTimeTo(registrationTimeTo);
            return createRecruitmentPO;
        }
    }
}