package com.xiaohuashifu.recruit.registration.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：报名表模板的数据对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class ApplicationFormTemplateDO {

    /**
     * 报名表模板编号
     */
    private Long id;

    /**
     * 招新编号
     */
    private Long recruitmentId;

    /**
     * 报名提示
     */
    private String prompt;

    /**
     * 是否需要头像
     */
    private Boolean avatar;

    /**
     * 是否需要姓名
     */
    private Boolean fullName;

    /**
     * 是否需要手机号码
     */
    private Boolean phone;

    /**
     * 是否需要第一部门
     */
    private Boolean firstDepartment;

    /**
     * 是否需要第二部门
     */
    private Boolean secondDepartment;

    /**
     * 是否需要邮箱
     */
    private Boolean email;

    /**
     * 是否需要个人简介
     */
    private Boolean introduction;

    /**
     * 是否需要附件
     */
    private Boolean attachment;

    /**
     * 是否需要学号
     */
    private Boolean studentNumber;

    /**
     * 是否需要学院
     */
    private Boolean college;

    /**
     * 是否需要专业
     */
    private Boolean major;

    /**
     * 是否需要备注
     */
    private Boolean note;

    /**
     * 该报名表模板是否被停用
     */
    private Boolean deactivated;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Boolean getAvatar() {
        return avatar;
    }

    public void setAvatar(Boolean avatar) {
        this.avatar = avatar;
    }

    public Boolean getFullName() {
        return fullName;
    }

    public void setFullName(Boolean fullName) {
        this.fullName = fullName;
    }

    public Boolean getPhone() {
        return phone;
    }

    public void setPhone(Boolean phone) {
        this.phone = phone;
    }

    public Boolean getFirstDepartment() {
        return firstDepartment;
    }

    public void setFirstDepartment(Boolean firstDepartment) {
        this.firstDepartment = firstDepartment;
    }

    public Boolean getSecondDepartment() {
        return secondDepartment;
    }

    public void setSecondDepartment(Boolean secondDepartment) {
        this.secondDepartment = secondDepartment;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Boolean introduction) {
        this.introduction = introduction;
    }

    public Boolean getAttachment() {
        return attachment;
    }

    public void setAttachment(Boolean attachment) {
        this.attachment = attachment;
    }

    public Boolean getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Boolean studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Boolean getCollege() {
        return college;
    }

    public void setCollege(Boolean college) {
        this.college = college;
    }

    public Boolean getMajor() {
        return major;
    }

    public void setMajor(Boolean major) {
        this.major = major;
    }

    public Boolean getNote() {
        return note;
    }

    public void setNote(Boolean note) {
        this.note = note;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
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
        return "ApplicationFormTemplateDO{" +
                "id=" + id +
                ", recruitmentId=" + recruitmentId +
                ", prompt='" + prompt + '\'' +
                ", avatar=" + avatar +
                ", fullName=" + fullName +
                ", phone=" + phone +
                ", firstDepartment=" + firstDepartment +
                ", secondDepartment=" + secondDepartment +
                ", email=" + email +
                ", introduction=" + introduction +
                ", attachment=" + attachment +
                ", studentNumber=" + studentNumber +
                ", college=" + college +
                ", major=" + major +
                ", note=" + note +
                ", deactivated=" + deactivated +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Long recruitmentId;
        private String prompt;
        private Boolean avatar;
        private Boolean fullName;
        private Boolean phone;
        private Boolean firstDepartment;
        private Boolean secondDepartment;
        private Boolean email;
        private Boolean introduction;
        private Boolean attachment;
        private Boolean studentNumber;
        private Boolean college;
        private Boolean major;
        private Boolean note;
        private Boolean deactivated;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder recruitmentId(Long recruitmentId) {
            this.recruitmentId = recruitmentId;
            return this;
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder avatar(Boolean avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder fullName(Boolean fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder phone(Boolean phone) {
            this.phone = phone;
            return this;
        }

        public Builder firstDepartment(Boolean firstDepartment) {
            this.firstDepartment = firstDepartment;
            return this;
        }

        public Builder secondDepartment(Boolean secondDepartment) {
            this.secondDepartment = secondDepartment;
            return this;
        }

        public Builder email(Boolean email) {
            this.email = email;
            return this;
        }

        public Builder introduction(Boolean introduction) {
            this.introduction = introduction;
            return this;
        }

        public Builder attachment(Boolean attachment) {
            this.attachment = attachment;
            return this;
        }

        public Builder studentNumber(Boolean studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public Builder college(Boolean college) {
            this.college = college;
            return this;
        }

        public Builder major(Boolean major) {
            this.major = major;
            return this;
        }

        public Builder note(Boolean note) {
            this.note = note;
            return this;
        }

        public Builder deactivated(Boolean deactivated) {
            this.deactivated = deactivated;
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

        public ApplicationFormTemplateDO build() {
            ApplicationFormTemplateDO applicationFormTemplateDO = new ApplicationFormTemplateDO();
            applicationFormTemplateDO.setId(id);
            applicationFormTemplateDO.setRecruitmentId(recruitmentId);
            applicationFormTemplateDO.setPrompt(prompt);
            applicationFormTemplateDO.setAvatar(avatar);
            applicationFormTemplateDO.setFullName(fullName);
            applicationFormTemplateDO.setPhone(phone);
            applicationFormTemplateDO.setFirstDepartment(firstDepartment);
            applicationFormTemplateDO.setSecondDepartment(secondDepartment);
            applicationFormTemplateDO.setEmail(email);
            applicationFormTemplateDO.setIntroduction(introduction);
            applicationFormTemplateDO.setAttachment(attachment);
            applicationFormTemplateDO.setStudentNumber(studentNumber);
            applicationFormTemplateDO.setCollege(college);
            applicationFormTemplateDO.setMajor(major);
            applicationFormTemplateDO.setNote(note);
            applicationFormTemplateDO.setDeactivated(deactivated);
            applicationFormTemplateDO.setCreateTime(createTime);
            applicationFormTemplateDO.setUpdateTime(updateTime);
            return applicationFormTemplateDO;
        }
    }
}
