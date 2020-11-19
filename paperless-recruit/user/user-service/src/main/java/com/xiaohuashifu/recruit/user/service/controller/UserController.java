package com.xiaohuashifu.recruit.user.service.controller;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
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
    @PreAuthorize("hasAuthority('test3')")
    public Object getUser(Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/email")
    public Object updateEmail(String email) {
        final Result<UserDTO> updateEmailResult = userService.updateEmail(1L, email,"12233");
        return updateEmailResult;
    }

}
