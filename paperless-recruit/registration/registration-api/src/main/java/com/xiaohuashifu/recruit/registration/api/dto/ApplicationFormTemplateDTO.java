package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 描述：报名表模板的数据传输对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ApplicationFormTemplateDTO implements Serializable {

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

}
