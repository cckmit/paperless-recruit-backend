package com.xiaohuashifu.recruit.registration.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ApplicationFormTemplateRequest implements Serializable {

    /**
     * 是否需要头像
     */
    @NotNull
    protected Boolean avatar;

    /**
     * 是否需要姓名
     */
    @NotNull
    protected Boolean fullName;

    /**
     * 是否需要手机号码
     */
    @NotNull
    protected Boolean phone;

    /**
     * 是否需要第一部门
     */
    @NotNull
    protected Boolean firstDepartment;

    /**
     * 是否需要第二部门
     */
    @NotNull
    protected Boolean secondDepartment;

    /**
     * 是否需要邮箱
     */
    @NotNull
    protected Boolean email;

    /**
     * 是否需要个人简介
     */
    @NotNull
    protected Boolean introduction;

    /**
     * 是否需要附件
     */
    @NotNull
    protected Boolean attachment;

    /**
     * 是否需要学号
     */
    @NotNull
    protected Boolean studentNumber;

    /**
     * 是否需要学院
     */
    @NotNull
    protected Boolean college;

    /**
     * 是否需要专业
     */
    @NotNull
    protected Boolean major;

    /**
     * 是否需要备注
     */
    @NotNull
    protected Boolean note;
}
