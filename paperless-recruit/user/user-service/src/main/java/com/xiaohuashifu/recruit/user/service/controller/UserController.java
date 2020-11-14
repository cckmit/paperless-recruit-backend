package com.xiaohuashifu.recruit.user.service.controller;

import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/14 14:15
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public Object getUser(Long id) {
        return userService.getUser(id);
    }

}
