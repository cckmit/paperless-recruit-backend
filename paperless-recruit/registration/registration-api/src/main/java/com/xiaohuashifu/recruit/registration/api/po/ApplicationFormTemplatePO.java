package com.xiaohuashifu.recruit.registration.api.po;

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
}
