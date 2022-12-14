package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 描述：与密码有关的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/16 20:40
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
