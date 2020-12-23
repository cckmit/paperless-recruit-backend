package com.xiaohuashifu.recruit.registration.service.do0;

import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;

import java.time.LocalDateTime;

/**
 * 描述：招新数据对象
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
public class RecruitmentDO {
    private Long id;
    private Long organizationId;
    private Long organizationPositionId;
    private Integer recruitNumbers;
    private String positionDuty;
    private String positionRequirement;
    private LocalDateTime registrationTimeFrom;
    private LocalDateTime registrationTimeTo;
    private LocalDateTime releaseTime;
    private String recruitmentGrades;
    private String recruitmentDepartmentIds;
    private String recruitmentCollegeIds;
    private String recruitmentMajorIds;
    private RecruitmentStatusEnum recruitmentStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationPositionId() {
        return organizationPositionId;
    }

    public void setOrganizationPositionId(Long organizationPositionId) {
        this.organizationPositionId = organizationPositionId;
    }

    public Integer getRecruitNumbers() {
        return recruitNumbers;
    }

    public void setRecruitNumbers(Integer recruitNumbers) {
        this.recruitNumbers = recruitNumbers;
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

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRecruitmentGrades() {
        return recruitmentGrades;
    }

    public void setRecruitmentGrades(String recruitmentGrades) {
        this.recruitmentGrades = recruitmentGrades;
    }

    public String getRecruitmentDepartmentIds() {
        return recruitmentDepartmentIds;
    }

    public void setRecruitmentDepartmentIds(String recruitmentDepartmentIds) {
        this.recruitmentDepartmentIds = recruitmentDepartmentIds;
    }

    public String getRecruitmentCollegeIds() {
        return recruitmentCollegeIds;
    }

    public void setRecruitmentCollegeIds(String recruitmentCollegeIds) {
        this.recruitmentCollegeIds = recruitmentCollegeIds;
    }

    public String getRecruitmentMajorIds() {
        return recruitmentMajorIds;
    }

    public void setRecruitmentMajorIds(String recruitmentMajorIds) {
        this.recruitmentMajorIds = recruitmentMajorIds;
    }

    public RecruitmentStatusEnum getRecruitmentStatus() {
        return recruitmentStatus;
    }

    public void setRecruitmentStatus(RecruitmentStatusEnum recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RecruitmentDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", organizationPositionId=" + organizationPositionId +
                ", recruitNumbers=" + recruitNumbers +
                ", positionDuty='" + positionDuty + '\'' +
                ", positionRequirement='" + positionRequirement + '\'' +
                ", registrationTimeFrom=" + registrationTimeFrom +
                ", registrationTimeTo=" + registrationTimeTo +
                ", releaseTime=" + releaseTime +
                ", recruitmentGrades='" + recruitmentGrades + '\'' +
                ", recruitmentDepartmentIds='" + recruitmentDepartmentIds + '\'' +
                ", recruitmentCollegeIds='" + recruitmentCollegeIds + '\'' +
                ", recruitmentMajorIds='" + recruitmentMajorIds + '\'' +
                ", recruitmentStatus=" + recruitmentStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private Long organizationPositionId;
        private Integer recruitNumbers;
        private String positionDuty;
        private String positionRequirement;
        private LocalDateTime registrationTimeFrom;
        private LocalDateTime registrationTimeTo;
        private LocalDateTime releaseTime;
        private String recruitmentGrades;
        private String recruitmentDepartmentIds;
        private String recruitmentCollegeIds;
        private String recruitmentMajorIds;
        private RecruitmentStatusEnum recruitmentStatus;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder organizationPositionId(Long organizationPositionId) {
            this.organizationPositionId = organizationPositionId;
            return this;
        }

        public Builder recruitNumbers(Integer recruitNumbers) {
            this.recruitNumbers = recruitNumbers;
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

        public Builder registrationTimeFrom(LocalDateTime registrationTimeFrom) {
            this.registrationTimeFrom = registrationTimeFrom;
            return this;
        }

        public Builder registrationTimeTo(LocalDateTime registrationTimeTo) {
            this.registrationTimeTo = registrationTimeTo;
            return this;
        }

        public Builder releaseTime(LocalDateTime releaseTime) {
            this.releaseTime = releaseTime;
            return this;
        }

        public Builder recruitmentGrades(String recruitmentGrades) {
            this.recruitmentGrades = recruitmentGrades;
            return this;
        }

        public Builder recruitmentDepartmentIds(String recruitmentDepartmentIds) {
            this.recruitmentDepartmentIds = recruitmentDepartmentIds;
            return this;
        }

        public Builder recruitmentCollegeIds(String recruitmentCollegeIds) {
            this.recruitmentCollegeIds = recruitmentCollegeIds;
            return this;
        }

        public Builder recruitmentMajorIds(String recruitmentMajorIds) {
            this.recruitmentMajorIds = recruitmentMajorIds;
            return this;
        }

        public Builder recruitmentStatus(RecruitmentStatusEnum recruitmentStatus) {
            this.recruitmentStatus = recruitmentStatus;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public RecruitmentDO build() {
            RecruitmentDO recruitmentDO = new RecruitmentDO();
            recruitmentDO.setId(id);
            recruitmentDO.setOrganizationId(organizationId);
            recruitmentDO.setOrganizationPositionId(organizationPositionId);
            recruitmentDO.setRecruitNumbers(recruitNumbers);
            recruitmentDO.setPositionDuty(positionDuty);
            recruitmentDO.setPositionRequirement(positionRequirement);
            recruitmentDO.setRegistrationTimeFrom(registrationTimeFrom);
            recruitmentDO.setRegistrationTimeTo(registrationTimeTo);
            recruitmentDO.setReleaseTime(releaseTime);
            recruitmentDO.setRecruitmentGrades(recruitmentGrades);
            recruitmentDO.setRecruitmentDepartmentIds(recruitmentDepartmentIds);
            recruitmentDO.setRecruitmentCollegeIds(recruitmentCollegeIds);
            recruitmentDO.setRecruitmentMajorIds(recruitmentMajorIds);
            recruitmentDO.setRecruitmentStatus(recruitmentStatus);
            recruitmentDO.setCreateTime(createTime);
            recruitmentDO.setUpdateTime(updateTime);
            return recruitmentDO;
        }
    }
}
