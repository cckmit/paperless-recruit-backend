package com.xiaohuashifu.recruit.authentication.service.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:00
 */
@RestController
public class UserController {
    @GetMapping("index")
    public Object index(Authentication authentication) {
        return authentication;
    }
}
