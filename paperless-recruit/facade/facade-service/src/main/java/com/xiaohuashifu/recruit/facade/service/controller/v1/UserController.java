package com.xiaohuashifu.recruit.facade.service.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/11/28 14:41
 */
@RestController
public class UserController {

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('create_user')")
    public Object getUser() {
        return "user";
    }
}
