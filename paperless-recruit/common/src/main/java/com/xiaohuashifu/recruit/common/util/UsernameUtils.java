package com.xiaohuashifu.recruit.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 描述：用户名相关工具类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 18:19
 */
public class UsernameUtils {
    /**
     * 随机用户名，格式为scau_recruit_{10位随机数字}
     *
     * @return 用户名
     */
    public static String randomUsername() {
        return "scau_recruit_" + RandomStringUtils.randomNumeric(10);
    }
}
