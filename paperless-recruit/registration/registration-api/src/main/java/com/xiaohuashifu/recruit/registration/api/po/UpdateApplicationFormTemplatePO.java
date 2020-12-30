package com.xiaohuashifu.recruit.registration.api.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：更新报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class UpdateApplicationFormTemplatePO extends ApplicationFormTemplatePO {

    /**
     * 报名表模板编号
     */
    @NotNull(message = "The id can't be null.")
    @Positive(message = "The id must be greater than 0.")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateApplicationFormTemplatePO{" +
                "id=" + id +
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
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
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

        public Builder id(Long id) {
            this.id = id;
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

        public UpdateApplicationFormTemplatePO build() {
            UpdateApplicationFormTemplatePO updateApplicationFormTemplatePO = new UpdateApplicationFormTemplatePO();
            updateApplicationFormTemplatePO.setId(id);
            updateApplicationFormTemplatePO.setAvatar(avatar);
            updateApplicationFormTemplatePO.setFullName(fullName);
            updateApplicationFormTemplatePO.setPhone(phone);
            updateApplicationFormTemplatePO.setFirstDepartment(firstDepartment);
            updateApplicationFormTemplatePO.setSecondDepartment(secondDepartment);
            updateApplicationFormTemplatePO.setEmail(email);
            updateApplicationFormTemplatePO.setIntroduction(introduction);
            updateApplicationFormTemplatePO.setAttachment(attachment);
            updateApplicationFormTemplatePO.setStudentNumber(studentNumber);
            updateApplicationFormTemplatePO.setCollege(college);
            updateApplicationFormTemplatePO.setMajor(major);
            updateApplicationFormTemplatePO.setNote(note);
            return updateApplicationFormTemplatePO;
        }
    }
}
