package com.xiaohuashifu.recruit.registration.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：报名表的数据对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class ApplicationFormDO {

    /**
     * 报名表模板编号
     */
    private Long id;

    /**
     * 报名者用户编号
     */
    private Long userId;

    /**
     * 招新编号
     */
    private Long recruitmentId;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 第一部门
     */
    private Long firstDepartmentId;

    /**
     * 第二部门
     */
    private Long secondDepartmentId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 附件地址
     */
    private String attachmentUrl;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院
     */
    private Long collegeId;

    /**
     * 专业
     */
    private Long majorId;

    /**
     * 备注
     */
    private String note;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getFirstDepartmentId() {
        return firstDepartmentId;
    }

    public void setFirstDepartmentId(Long firstDepartmentId) {
        this.firstDepartmentId = firstDepartmentId;
    }

    public Long getSecondDepartmentId() {
        return secondDepartmentId;
    }

    public void setSecondDepartmentId(Long secondDepartmentId) {
        this.secondDepartmentId = secondDepartmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        return "ApplicationFormDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", recruitmentId=" + recruitmentId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", firstDepartmentId=" + firstDepartmentId +
                ", secondDepartmentId=" + secondDepartmentId +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
                ", note='" + note + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Long recruitmentId;
        private String avatarUrl;
        private String fullName;
        private String phone;
        private Long firstDepartmentId;
        private Long secondDepartmentId;
        private String email;
        private String introduction;
        private String attachmentUrl;
        private String studentNumber;
        private Long collegeId;
        private Long majorId;
        private String note;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder recruitmentId(Long recruitmentId) {
            this.recruitmentId = recruitmentId;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder firstDepartmentId(Long firstDepartmentId) {
            this.firstDepartmentId = firstDepartmentId;
            return this;
        }

        public Builder secondDepartmentId(Long secondDepartmentId) {
            this.secondDepartmentId = secondDepartmentId;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder attachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
            return this;
        }

        public Builder studentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public Builder collegeId(Long collegeId) {
            this.collegeId = collegeId;
            return this;
        }

        public Builder majorId(Long majorId) {
            this.majorId = majorId;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
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

        public ApplicationFormDO build() {
            ApplicationFormDO applicationFormDO = new ApplicationFormDO();
            applicationFormDO.setId(id);
            applicationFormDO.setUserId(userId);
            applicationFormDO.setRecruitmentId(recruitmentId);
            applicationFormDO.setAvatarUrl(avatarUrl);
            applicationFormDO.setFullName(fullName);
            applicationFormDO.setPhone(phone);
            applicationFormDO.setFirstDepartmentId(firstDepartmentId);
            applicationFormDO.setSecondDepartmentId(secondDepartmentId);
            applicationFormDO.setEmail(email);
            applicationFormDO.setIntroduction(introduction);
            applicationFormDO.setAttachmentUrl(attachmentUrl);
            applicationFormDO.setStudentNumber(studentNumber);
            applicationFormDO.setCollegeId(collegeId);
            applicationFormDO.setMajorId(majorId);
            applicationFormDO.setNote(note);
            applicationFormDO.setCreateTime(createTime);
            applicationFormDO.setUpdateTime(updateTime);
            return applicationFormDO;
        }
    }
}
