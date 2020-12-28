package com.xiaohuashifu.recruit.registration.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：招新数据传输对象
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
public class RecruitmentDTO implements Serializable {

    /**
     * 招新编号
     */
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 招新部门编号列表
     */
    private Set<Long> recruitmentDepartmentIds;

    /**
     * 职位名
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
     * @see com.xiaohuashifu.recruit.common.constant.GradeEnum
     * 招新年级
     */
    private Set<String> recruitmentGrades;

    /**
     * 招新学院编号列表
     */
    private Set<Long> recruitmentCollegeIds;

    /**
     * 招新专业编号列表
     */
    private Set<Long> recruitmentMajorIds;

    /**
     * 发布时间
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

    /**
     * @see com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum
     * 招新状态
     */
    private String recruitmentStatus;

    /**
     * 招新是否可用
     */
    private Boolean available;

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

    @Override
    public String toString() {
        return "RecruitmentDTO{" +
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

        public RecruitmentDTO build() {
            RecruitmentDTO recruitmentDTO = new RecruitmentDTO();
            recruitmentDTO.setId(id);
            recruitmentDTO.setOrganizationId(organizationId);
            recruitmentDTO.setRecruitmentDepartmentIds(recruitmentDepartmentIds);
            recruitmentDTO.setPositionName(positionName);
            recruitmentDTO.setRecruitmentNumbers(recruitmentNumbers);
            recruitmentDTO.setPositionDuty(positionDuty);
            recruitmentDTO.setPositionRequirement(positionRequirement);
            recruitmentDTO.setRecruitmentGrades(recruitmentGrades);
            recruitmentDTO.setRecruitmentCollegeIds(recruitmentCollegeIds);
            recruitmentDTO.setRecruitmentMajorIds(recruitmentMajorIds);
            recruitmentDTO.setReleaseTime(releaseTime);
            recruitmentDTO.setRegistrationTimeFrom(registrationTimeFrom);
            recruitmentDTO.setRegistrationTimeTo(registrationTimeTo);
            recruitmentDTO.setRecruitmentStatus(recruitmentStatus);
            recruitmentDTO.setAvailable(available);
            return recruitmentDTO;
        }
    }
}
