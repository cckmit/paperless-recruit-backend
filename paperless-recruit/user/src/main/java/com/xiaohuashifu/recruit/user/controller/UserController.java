package com.xiaohuashifu.recruit.user.controller;

import com.xiaohuashifu.recruit.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.dao.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@RestController
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        final UserQuery xhsf = new UserQuery.Builder().id(1L).username("xhsf").build();
        return "Hello Nacos Discovery " + userMapper.getUser(1);
    }
}
