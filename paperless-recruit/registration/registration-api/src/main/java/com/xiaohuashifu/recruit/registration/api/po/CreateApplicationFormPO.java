package com.xiaohuashifu.recruit.registration.api.po;

import java.io.Serializable;

/**
 * 描述：报名表的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class CreateApplicationFormPO implements Serializable {

    /**
     * 报名者用户编号
     */
    private Long userId;

    /**
     * 招新编号
     */
    private Long recruitmentId;

    /**
     * 头像
     */
    private ApplicationFormAvatarPO avatar;

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
     * 附件
     */
    private ApplicationFormAttachmentPO attachment;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 专业编号
     */
    private Long majorId;

    /**
     * 备注
     */
    private String note;

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

    public ApplicationFormAvatarPO getAvatar() {
        return avatar;
    }

    public void setAvatar(ApplicationFormAvatarPO avatar) {
        this.avatar = avatar;
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

    public ApplicationFormAttachmentPO getAttachment() {
        return attachment;
    }

    public void setAttachment(ApplicationFormAttachmentPO attachment) {
        this.attachment = attachment;
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

    @Override
    public String toString() {
        return "CreateApplicationFormPO{" +
                "userId=" + userId +
                ", recruitmentId=" + recruitmentId +
                ", avatar=" + avatar +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", firstDepartmentId=" + firstDepartmentId +
                ", secondDepartmentId=" + secondDepartmentId +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", attachment=" + attachment +
                ", studentNumber='" + studentNumber + '\'' +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
                ", note='" + note + '\'' +
                '}';
    }

    public static final class Builder {
        private Long userId;
        private Long recruitmentId;
        private ApplicationFormAvatarPO avatar;
        private String fullName;
        private String phone;
        private Long firstDepartmentId;
        private Long secondDepartmentId;
        private String email;
        private String introduction;
        private ApplicationFormAttachmentPO attachment;
        private String studentNumber;
        private Long collegeId;
        private Long majorId;
        private String note;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder recruitmentId(Long recruitmentId) {
            this.recruitmentId = recruitmentId;
            return this;
        }

        public Builder avatar(ApplicationFormAvatarPO avatar) {
            this.avatar = avatar;
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

        public Builder attachment(ApplicationFormAttachmentPO attachment) {
            this.attachment = attachment;
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

        public CreateApplicationFormPO build() {
            CreateApplicationFormPO createApplicationFormPO = new CreateApplicationFormPO();
            createApplicationFormPO.setUserId(userId);
            createApplicationFormPO.setRecruitmentId(recruitmentId);
            createApplicationFormPO.setAvatar(avatar);
            createApplicationFormPO.setFullName(fullName);
            createApplicationFormPO.setPhone(phone);
            createApplicationFormPO.setFirstDepartmentId(firstDepartmentId);
            createApplicationFormPO.setSecondDepartmentId(secondDepartmentId);
            createApplicationFormPO.setEmail(email);
            createApplicationFormPO.setIntroduction(introduction);
            createApplicationFormPO.setAttachment(attachment);
            createApplicationFormPO.setStudentNumber(studentNumber);
            createApplicationFormPO.setCollegeId(collegeId);
            createApplicationFormPO.setMajorId(majorId);
            createApplicationFormPO.setNote(note);
            return createApplicationFormPO;
        }
    }
}
