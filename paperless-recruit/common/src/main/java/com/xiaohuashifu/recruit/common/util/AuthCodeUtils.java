package com.xiaohuashifu.recruit.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 描述：验证码工具类，如手机验证码，邮箱验证码
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 16:08
 */
public class AuthCodeUtils {

    /**
     * 随机验证码，格式为6位数字
     * @return 验证码
     */
    public static String randomAuthCode() {
        return RandomStringUtils.randomNumeric(6);
    }

}
