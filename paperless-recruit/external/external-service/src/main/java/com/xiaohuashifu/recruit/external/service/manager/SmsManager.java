package com.xiaohuashifu.recruit.external.service.manager;

/**
 * 描述：短信相关服务封装
 *
 * @author xhsf
 * @create 2020/12/3 14:51
 */
public interface SmsManager {
    /**
     * 发送短信验证码
     *
     * @param phone 手机号码
     * @param authCode 验证码
     * @return 发送成功或者失败
     */
    boolean sendSmsAuthCode(String phone, String authCode);
}