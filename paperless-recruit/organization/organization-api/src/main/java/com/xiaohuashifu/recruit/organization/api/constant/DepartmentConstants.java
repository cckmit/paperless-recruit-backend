package com.xiaohuashifu.recruit.organization.api.constant;

/**
 * 描述：部门相关常量
 *
 * @author xhsf
 * @create 2020/12/10 16:15
 */
public class DepartmentConstants {

    /**
     * 没有部门时的部门编号
     */
    public static final long DEPARTMENT_ID_WHEN_NO_DEPARTMENT = 0;

    /**
     * 部门名最小长度
     */
    public static final int MIN_DEPARTMENT_NAME_LENGTH = 2;

    /**
     * 部门名最大长度
     */
    public static final int MAX_DEPARTMENT_NAME_LENGTH = 20;

    /**
     * 部门名缩写最小长度
     */
    public static final int MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH = 2;

    /**
     * 部门名缩写最大长度
     */
    public static final int MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH = 5;

    /**
     * 部门介绍最大长度
     */
    public static final int MAX_DEPARTMENT_INTRODUCTION_LENGTH = 200;

    /**
     * 部门 logo 最大长度，10MB
     */
    public static final int MAX_DEPARTMENT_LOGO_LENGTH = 10485760;

    /**
     * 部门最大的标签数
     */
    public static final int MAX_DEPARTMENT_LABEL_NUMBER = 3;

}
