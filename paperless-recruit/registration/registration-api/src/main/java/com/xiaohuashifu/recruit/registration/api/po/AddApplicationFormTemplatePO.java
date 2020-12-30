package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：创建报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class AddApplicationFormTemplatePO extends ApplicationFormTemplatePO {

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

    @Override
    public String toString() {
        return "AddApplicationFormTemplatePO{" +
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

    public static Builder builder() {
        return new Builder();
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

        private Builder() {}

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
            AddApplicationFormTemplatePO addApplicationFormTemplatePO = new AddApplicationFormTemplatePO();
            addApplicationFormTemplatePO.setRecruitmentId(recruitmentId);
            addApplicationFormTemplatePO.setPrompt(prompt);
            addApplicationFormTemplatePO.setAvatar(avatar);
            addApplicationFormTemplatePO.setFullName(fullName);
            addApplicationFormTemplatePO.setPhone(phone);
            addApplicationFormTemplatePO.setFirstDepartment(firstDepartment);
            addApplicationFormTemplatePO.setSecondDepartment(secondDepartment);
            addApplicationFormTemplatePO.setEmail(email);
            addApplicationFormTemplatePO.setIntroduction(introduction);
            addApplicationFormTemplatePO.setAttachment(attachment);
            addApplicationFormTemplatePO.setStudentNumber(studentNumber);
            addApplicationFormTemplatePO.setCollege(college);
            addApplicationFormTemplatePO.setMajor(major);
            addApplicationFormTemplatePO.setNote(note);
            return addApplicationFormTemplatePO;
        }
    }
}
