package com.xiaohuashifu.recruit.user.controller;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.dao.UserMapper;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class UserController {

    private final UserMapper userMapper;

    private final Mapper mapper;

    public UserController(UserMapper userMapper, Mapper mapper) {
        this.userMapper = userMapper;
        this.mapper = mapper;
    }

    @GetMapping(value = "/echo/{string}")
    public Object echo(@PathVariable String string, @Id Long id) {
        System.out.println(id);
        return userMapper.getUser(1L);
    }
}
