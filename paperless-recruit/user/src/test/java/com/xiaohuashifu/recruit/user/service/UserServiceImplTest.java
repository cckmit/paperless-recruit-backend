package com.xiaohuashifu.recruit.user.service;

import com.xiaohuashifu.recruit.api.service.UserService;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.UserApplication;
import com.xiaohuashifu.recruit.user.UserApplicationTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 19:53
 */
class UserServiceImplTest extends UserApplicationTests{

    @Autowired
    private UserService userService;

    @Test
    void getUser() {
        final Result<UserDTO> user = userService.getUser(-1L);
        System.out.println(user);
    }

    @Test
    void testGetUser() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void changeUser() {
    }
}