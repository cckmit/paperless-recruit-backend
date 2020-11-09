package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.UserServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 19:53
 */
public class UserServiceTest extends UserServiceApplicationTests {

    @Reference
    private UserService userService;

    @Test
    public void getUser() {
        final Result<UserDTO> user = userService.getUser(-1L);
        System.out.println(user);
    }

    @Test
    public void saveUser() {
        final Result<UserDTO> saveUserResult = userService.saveUser("xhsf1", "311211");
        System.out.println(saveUserResult);
    }

    @Test
    public void changeUser() {
    }

    @Test
    public void getUserByUsername() {
        // 正确
        Result<UserDTO> getUserResult = userService.getUserByUsername("xiaohuashifu");
        assertTrue(getUserResult.isSuccess());
        UserDTO user = getUserResult.getData();
        assertEquals("xiaohuashifu", user.getUsername());
        assertEquals(Long.valueOf(1), user.getId());

        // 参数错误，无法通过参数校验
        getUserResult = userService.getUserByUsername("xia");
        assertFalse(getUserResult.isSuccess());
        System.out.println(getUserResult);
    }

    @Test
    public void updateUsername() {
        final Result<UserDTO> updateUsernameResult = userService.updateUsername(-3L, "xhsfnew");
        assertTrue(updateUsernameResult.isSuccess());
    }

    @Test
    public void updatePassword() {
        final Result<UserDTO> updatePasswordResult = userService.updatePassword(3L, "323333");
        assertTrue(updatePasswordResult.isSuccess());
    }

    @Test
    public void updateAvailableState() {
        final Result<UserDTO> updateAvailableStateResult = userService.updateAvailableState(3L, false);
        assertTrue(updateAvailableStateResult.isSuccess());
    }
}