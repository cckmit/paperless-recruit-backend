package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 描述：与密码有关的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/16 20:40
 */
@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordEncoder passwordEncoder;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 对密码进行编码
     * @param password 原密码
     * @return 编码后的密码
     */
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
