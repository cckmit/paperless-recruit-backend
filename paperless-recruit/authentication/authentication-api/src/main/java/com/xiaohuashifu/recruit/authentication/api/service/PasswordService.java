package com.xiaohuashifu.recruit.authentication.api.service;

/**
 * 描述：与密码有关的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/16 20:39
 */
public interface PasswordService {

    /**
     * 对密码进行编码
     *
     * @param password 原密码
     * @return 编码后的密码
     */
    String encodePassword(String password);

}
