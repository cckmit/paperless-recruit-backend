package com.xiaohuashifu.recruit.external.api.constant;

/**
 * 描述：邮件服务相关常量
 *
 * @author xhsf
 * @create 2020/12/10 13:23
 */
public class EmailServiceConstants {

    /**
     * 邮件验证码主题最大长度
     */
    public static final int MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH = 100;

    /**
     * 邮件验证码标题最大长度
     */
    public static final int MAX_EMAIL_AUTH_CODE_TITLE_LENGTH = 10;

    /**
     * 邮件验证码最大过期时间
     */
    public static final int MAX_EMAIL_AUTH_CODE_EXPIRATION_TIME = 10;

    /**
     * 邮件主题最大长度
     */
    public static final int MAX_EMAIL_SUBJECT_LENGTH = 78;

    /**
     * 邮件内容最大长度
     */
    public static final int MAX_EMAIL_TEXT_LENGTH = 10000;

    /**
     * 邮件附件最大个数
     */
    public static final int MAX_EMAIL_ATTACHMENT_NUMBER = 10;

    /**
     * 邮件附件最大大小，100MB
     */
    public static final int MAX_EMAIL_ATTACHMENT_LENGTH = 104857600;

}
