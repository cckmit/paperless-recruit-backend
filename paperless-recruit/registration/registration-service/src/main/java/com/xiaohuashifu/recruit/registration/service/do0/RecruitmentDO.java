package com.xiaohuashifu.recruit.registration.service.do0;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：招新数据对象
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
public class RecruitmentDO {
    private Long id;
    private Long organizationId;
    private Set<Long> recruitmentDepartmentIds;
    private String positionName;
    private String recruitmentNumbers;
    private String positionDuty;
    private String positionRequirement;
    private Set<String> recruitmentGrades;
    private Set<Long> recruitmentCollegeIds;
    private Set<Long> recruitmentMajorIds;
    private LocalDateTime releaseTime;
    private LocalDateTime registrationTimeFrom;
    private LocalDateTime registrationTimeTo;
    private String recruitmentStatus;
    private Boolean available;
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

    public Set<Long> getRecruitmentDepartmentIds() {
        return recruitmentDepartmentIds;
    }

    public void setRecruitmentDepartmentIds(Set<Long> recruitmentDepartmentIds) {
        this.recruitmentDepartmentIds = recruitmentDepartmentIds;
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

    public Set<String> getRecruitmentGrades() {
        return recruitmentGrades;
    }

    public void setRecruitmentGrades(Set<String> recruitmentGrades) {
        this.recruitmentGrades = recruitmentGrades;
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

    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }

    public void setRecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
                ", recruitmentDepartmentIds=" + recruitmentDepartmentIds +
                ", positionName='" + positionName + '\'' +
                ", recruitmentNumbers='" + recruitmentNumbers + '\'' +
                ", positionDuty='" + positionDuty + '\'' +
                ", positionRequirement='" + positionRequirement + '\'' +
                ", recruitmentGrades=" + recruitmentGrades +
                ", recruitmentCollegeIds=" + recruitmentCollegeIds +
                ", recruitmentMajorIds=" + recruitmentMajorIds +
                ", releaseTime=" + releaseTime +
                ", registrationTimeFrom=" + registrationTimeFrom +
                ", registrationTimeTo=" + registrationTimeTo +
                ", recruitmentStatus='" + recruitmentStatus + '\'' +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private Set<Long> recruitmentDepartmentIds;
        private String positionName;
        private String recruitmentNumbers;
        private String positionDuty;
        private String positionRequirement;
        private Set<String> recruitmentGrades;
        private Set<Long> recruitmentCollegeIds;
        private Set<Long> recruitmentMajorIds;
        private LocalDateTime releaseTime;
        private LocalDateTime registrationTimeFrom;
        private LocalDateTime registrationTimeTo;
        private String recruitmentStatus;
        private Boolean available;
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

        public Builder recruitmentDepartmentIds(Set<Long> recruitmentDepartmentIds) {
            this.recruitmentDepartmentIds = recruitmentDepartmentIds;
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

        public Builder recruitmentGrades(Set<String> recruitmentGrades) {
            this.recruitmentGrades = recruitmentGrades;
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

        public Builder recruitmentStatus(String recruitmentStatus) {
            this.recruitmentStatus = recruitmentStatus;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
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
            recruitmentDO.setRecruitmentDepartmentIds(recruitmentDepartmentIds);
            recruitmentDO.setPositionName(positionName);
            recruitmentDO.setRecruitmentNumbers(recruitmentNumbers);
            recruitmentDO.setPositionDuty(positionDuty);
            recruitmentDO.setPositionRequirement(positionRequirement);
            recruitmentDO.setRecruitmentGrades(recruitmentGrades);
            recruitmentDO.setRecruitmentCollegeIds(recruitmentCollegeIds);
            recruitmentDO.setRecruitmentMajorIds(recruitmentMajorIds);
            recruitmentDO.setReleaseTime(releaseTime);
            recruitmentDO.setRegistrationTimeFrom(registrationTimeFrom);
            recruitmentDO.setRegistrationTimeTo(registrationTimeTo);
            recruitmentDO.setRecruitmentStatus(recruitmentStatus);
            recruitmentDO.setAvailable(available);
            recruitmentDO.setCreateTime(createTime);
            recruitmentDO.setUpdateTime(updateTime);
            return recruitmentDO;
        }
    }
}
