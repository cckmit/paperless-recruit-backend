package com.xiaohuashifu.recruit.registration.api.po;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
public class ApplicationFormTemplatePO implements Serializable {

    /**
     * 是否需要头像
     */
    @NotNull(message = "The avatar can't be null.")
    protected Boolean avatar;

    /**
     * 是否需要姓名
     */
    @NotNull(message = "The fullName can't be null.")
    protected Boolean fullName;

    /**
     * 是否需要手机号码
     */
    @NotNull(message = "The phone can't be null.")
    protected Boolean phone;

    /**
     * 是否需要第一部门
     */
    @NotNull(message = "The firstDepartment can't be null.")
    protected Boolean firstDepartment;

    /**
     * 是否需要第二部门
     */
    @NotNull(message = "The secondDepartment can't be null.")
    protected Boolean secondDepartment;

    /**
     * 是否需要邮箱
     */
    @NotNull(message = "The email can't be null.")
    protected Boolean email;

    /**
     * 是否需要个人简介
     */
    @NotNull(message = "The introduction can't be null.")
    protected Boolean introduction;

    /**
     * 是否需要附件
     */
    @NotNull(message = "The attachment can't be null.")
    protected Boolean attachment;

    /**
     * 是否需要学号
     */
    @NotNull(message = "The studentNumber can't be null.")
    protected Boolean studentNumber;

    /**
     * 是否需要学院
     */
    @NotNull(message = "The college can't be null.")
    protected Boolean college;

    /**
     * 是否需要专业
     */
    @NotNull(message = "The major can't be null.")
    protected Boolean major;

    /**
     * 是否需要备注
     */
    @NotNull(message = "The note can't be null.")
    protected Boolean note;

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
        return "ApplicationFormTemplatePO{" +
                "avatar=" + avatar +
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

}
