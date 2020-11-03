package com.xiaohuashifu.recruit.user.service;

import com.xiaohuashifu.recruit.api.service.UserService;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.UserApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 19:53
 */
public class UserServiceImplTest extends UserApplicationTests{

    @Reference
    private UserService userService;

    @Test
    public void getUser() {
        final Result<UserDTO> user = userService.getUser(1L);
        System.out.println(user);
    }

    @Test
    public void testGetUser() {
    }

    @Test
    public void saveUser() {
    }

    @Test
    public void changeUser() {
    }
}