package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：创建报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class AddApplicationFormTemplatePO implements Serializable {

    /**
     * 招新编号
     */
    @NotNull(message = "The recruitmentId can't be null.")
    @Positive(message = "The recruitmentId must be greater than 0.")
    private Long recruitmentId;

    /**
     * 报名提示
     */
    @NotBlank(message = "The prompt can't be blank.")
    @Size(max = ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH,
            message = "The length of prompt must not be greater than "
                    + ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH + ".")
    private String prompt;

    /**
     * 是否需要头像
     */
    @NotNull(message = "The avatar can't be null.")
    private Boolean avatar;

    /**
     * 是否需要姓名
     */
    @NotNull(message = "The fullName can't be null.")
    private Boolean fullName;

    /**
     * 是否需要手机号码
     */
    @NotNull(message = "The phone can't be null.")
    private Boolean phone;

    /**
     * 是否需要第一部门
     */
    @NotNull(message = "The firstDepartment can't be null.")
    private Boolean firstDepartment;

    /**
     * 是否需要第二部门
     */
    @NotNull(message = "The secondDepartment can't be null.")
    private Boolean secondDepartment;

    /**
     * 是否需要邮箱
     */
    @NotNull(message = "The email can't be null.")
    private Boolean email;

    /**
     * 是否需要个人简介
     */
    @NotNull(message = "The introduction can't be null.")
    private Boolean introduction;

    /**
     * 是否需要附件
     */
    @NotNull(message = "The attachment can't be null.")
    private Boolean attachment;

    /**
     * 是否需要学号
     */
    @NotNull(message = "The studentNumber can't be null.")
    private Boolean studentNumber;

    /**
     * 是否需要学院
     */
    @NotNull(message = "The college can't be null.")
    private Boolean college;

    /**
     * 是否需要专业
     */
    @NotNull(message = "The major can't be null.")
    private Boolean major;

    /**
     * 是否需要备注
     */
    @NotNull(message = "The note can't be null.")
    private Boolean note;

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

    @Override
    public String toString() {
        return "CreateApplicationFormTemplatePO{" +
                "recruitmentId=" + recruitmentId +
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
                '}';
    }

    public static final class Builder {
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

        public AddApplicationFormTemplatePO build() {
            AddApplicationFormTemplatePO createApplicationFormTemplatePO = new AddApplicationFormTemplatePO();
            createApplicationFormTemplatePO.setRecruitmentId(recruitmentId);
            createApplicationFormTemplatePO.setPrompt(prompt);
            createApplicationFormTemplatePO.setAvatar(avatar);
            createApplicationFormTemplatePO.setFullName(fullName);
            createApplicationFormTemplatePO.setPhone(phone);
            createApplicationFormTemplatePO.setFirstDepartment(firstDepartment);
            createApplicationFormTemplatePO.setSecondDepartment(secondDepartment);
            createApplicationFormTemplatePO.setEmail(email);
            createApplicationFormTemplatePO.setIntroduction(introduction);
            createApplicationFormTemplatePO.setAttachment(attachment);
            createApplicationFormTemplatePO.setStudentNumber(studentNumber);
            createApplicationFormTemplatePO.setCollege(college);
            createApplicationFormTemplatePO.setMajor(major);
            createApplicationFormTemplatePO.setNote(note);
            return createApplicationFormTemplatePO;
        }
    }
}
