package com.xiaohuashifu.recruit.user.api.constant;

/**
 * 描述：用户个人信息相关常量
 *
 * @author xhsf
 * @create 2020/12/10 16:50
 */
public class UserProfileConstants {

    /**
     * 最小姓名长度
     */
    public static final int MIN_FULL_NAME_LENGTH = 2;

    /**
     * 最大姓名长度
     */
    public static final int MAX_FULL_NAME_LENGTH = 5;

    /**
     * 最大个人介绍长度
     */
    public static final int MAX_INTRODUCTION_LENGTH = 400;

    /**
     * 学号的正则表达式
     */
    public static final String STUDENT_NUMBER_REGEXP = "^20\\d{10}$";

}
