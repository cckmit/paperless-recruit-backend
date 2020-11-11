package com.xiaohuashifu.recruit.authentication.service.service.constant;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:23
 */
public class PhoneLoginServiceConstant {

    /**
     * 短信验证码的redis key前缀
     * 格式为login:phone:authCode:{phone}
     */
    public static final String MESSAGE_AUTH_CODE_REDIS_PREFIX = "login:phone:authCode:";

}
