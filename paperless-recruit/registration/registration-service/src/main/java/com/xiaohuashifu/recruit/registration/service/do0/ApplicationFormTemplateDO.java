package com.xiaohuashifu.recruit.registration.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：报名表模板的数据对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("application_form_template")
public class ApplicationFormTemplateDO {

    /**
     * 报名表模板编号
     */
    @TableId(type = IdType.AUTO)
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
    @TableField("is_avatar")
    private Boolean avatar;

    /**
     * 是否需要姓名
     */
    @TableField("is_full_name")
    private Boolean fullName;

    /**
     * 是否需要手机号码
     */
    @TableField("is_phone")
    private Boolean phone;

    /**
     * 是否需要第一部门
     */
    @TableField("is_first_department")
    private Boolean firstDepartment;

    /**
     * 是否需要第二部门
     */
    @TableField("is_second_department")
    private Boolean secondDepartment;

    /**
     * 是否需要邮箱
     */
    @TableField("is_email")
    private Boolean email;

    /**
     * 是否需要个人简介
     */
    @TableField("is_introduction")
    private Boolean introduction;

    /**
     * 是否需要附件
     */
    @TableField("is_attachment")
    private Boolean attachment;

    /**
     * 是否需要学号
     */
    @TableField("is_student_number")
    private Boolean studentNumber;

    /**
     * 是否需要学院
     */
    @TableField("is_college")
    private Boolean college;

    /**
     * 是否需要专业
     */
    @TableField("is_major")
    private Boolean major;

    /**
     * 是否需要备注
     */
    @TableField("is_note")
    private Boolean note;

    /**
     * 该报名表模板是否被停用
     */
    @TableField("is_deactivated")
    private Boolean deactivated;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
