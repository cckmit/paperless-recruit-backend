package com.xiaohuashifu.recruit.registration.api.constant;

/**
 * 描述：报名表相关常量
 *
 * @author xhsf
 * @create 2020/12/28 22:14
 */
public class ApplicationFormConstants {

    /**
     * 最大个人简介长度
     */
    public static final int MAX_INTRODUCTION_LENGTH = 400;

    /**
     * 最大备注长度
     */
    public static final int MAX_NOTE_LENGTH = 100;

    /**
     * 头像 url 的匹配模式
     */
    public static final String AVATAR_URL_PATTERN = "(application-forms/avatars/)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)";

    /**
     * 附件 url 的匹配模式
     */
    public static final String ATTACHMENT_URL_PATTERN = "(application-forms/attachments/)(.+)(\\.rar|\\.zip)";

    /**
     * 最大学院长度
     */
    public static final int MAX_COLLEGE_LENGTH = 20;

    /**
     * 最大专业长度
     */
    public static final int MAX_MAJOR_LENGTH = 20;

}
