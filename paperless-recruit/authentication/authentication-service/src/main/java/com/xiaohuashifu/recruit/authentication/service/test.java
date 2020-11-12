package com.xiaohuashifu.recruit.authentication.service;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 16:39
 */
public class test {

    public static void main(String[] args) {
        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test1234"));
    }
}
