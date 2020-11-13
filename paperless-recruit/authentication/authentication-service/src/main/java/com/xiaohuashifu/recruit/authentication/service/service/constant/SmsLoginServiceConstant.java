package com.xiaohuashifu.recruit.authentication.service.service.constant;

/**
 * 描述：短信登录的一些常量
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:23
 */
public class SmsLoginServiceConstant {

    /**
     * 短信验证码的redis key前缀
     * 格式为login:sms:authCode:{phone}
     */
    public static final String SMS_AUTH_CODE_REDIS_PREFIX = "login:sms:authCode:";

}
