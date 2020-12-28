package com.xiaohuashifu.recruit.registration.api.dto;

import java.io.Serializable;

/**
 * 描述：报名表的数据传输对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class ApplicationFormDTO implements Serializable {

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
    private String firstDepartment;

    /**
     * 第二部门
     */
    private String secondDepartment;

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
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 备注
     */
    private String note;

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

    public String getFirstDepartment() {
        return firstDepartment;
    }

    public void setFirstDepartment(String firstDepartment) {
        this.firstDepartment = firstDepartment;
    }

    public String getSecondDepartment() {
        return secondDepartment;
    }

    public void setSecondDepartment(String secondDepartment) {
        this.secondDepartment = secondDepartment;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ApplicationFormDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", recruitmentId=" + recruitmentId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", firstDepartment='" + firstDepartment + '\'' +
                ", secondDepartment='" + secondDepartment + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", attachmentUrl='" + attachmentUrl + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Long recruitmentId;
        private String avatarUrl;
        private String fullName;
        private String phone;
        private String firstDepartment;
        private String secondDepartment;
        private String email;
        private String introduction;
        private String attachmentUrl;
        private String studentNumber;
        private String college;
        private String major;
        private String note;

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

        public Builder firstDepartment(String firstDepartment) {
            this.firstDepartment = firstDepartment;
            return this;
        }

        public Builder secondDepartment(String secondDepartment) {
            this.secondDepartment = secondDepartment;
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

        public Builder college(String college) {
            this.college = college;
            return this;
        }

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public ApplicationFormDTO build() {
            ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO();
            applicationFormDTO.setId(id);
            applicationFormDTO.setUserId(userId);
            applicationFormDTO.setRecruitmentId(recruitmentId);
            applicationFormDTO.setAvatarUrl(avatarUrl);
            applicationFormDTO.setFullName(fullName);
            applicationFormDTO.setPhone(phone);
            applicationFormDTO.setFirstDepartment(firstDepartment);
            applicationFormDTO.setSecondDepartment(secondDepartment);
            applicationFormDTO.setEmail(email);
            applicationFormDTO.setIntroduction(introduction);
            applicationFormDTO.setAttachmentUrl(attachmentUrl);
            applicationFormDTO.setStudentNumber(studentNumber);
            applicationFormDTO.setCollege(college);
            applicationFormDTO.setMajor(major);
            applicationFormDTO.setNote(note);
            return applicationFormDTO;
        }
    }
}
