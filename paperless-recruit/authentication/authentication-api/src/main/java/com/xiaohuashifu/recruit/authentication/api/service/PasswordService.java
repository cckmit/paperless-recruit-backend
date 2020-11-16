package com.xiaohuashifu.recruit.authentication.api.service;

/**
 * 描述：与密码有关的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/16 20:39
 */
public interface PasswordService {
    /**
     * 对密码进行编码
     * @param password 原密码
     * @return 编码后的密码
     */
    default String encodePassword(String password) {
        throw new UnsupportedOperationException();
    }
}
