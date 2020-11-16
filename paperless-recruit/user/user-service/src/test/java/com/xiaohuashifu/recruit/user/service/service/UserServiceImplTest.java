package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 19:53
 */
public class UserServiceImplTest {

    private UserService userService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("userServiceTest");
        ReferenceConfig<UserService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://172.16.28.169:20881/com.xiaohuashifu.recruit.user.api.service.UserService");
        reference.setApplication(application);
        reference.setInterface(UserService.class);
        userService = reference.get();
    }
    
    @Test
    public void getUser() {
//        final Result<UserDTO> user = userService.getUser(1L);
//        System.out.println(user);
        System.out.println(userService.getUser(new UserQuery.Builder().phone("159923").build()));
    }

    @Test
    public void saveUser() {
        final Result<UserDTO> saveUserResult = userService.saveUser("xhsf4", "311211");
        System.out.println(saveUserResult);
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
    public void getUserByPhone() {
        System.out.println(userService.getUserByPhone("15992321303"));
    }

    @Test
    public void getUserByEmail() {
        System.out.println(userService.getUserByEmail("827032783@qq.com"));
    }

    @Test
    public void updateUsername() {
        final Result<UserDTO> updateUsernameResult = userService.updateUsername(-3L, "xhsfnew");
        assertTrue(updateUsernameResult.isSuccess());
    }

    @Test
    public void updatePhone() {
        final Result<UserDTO> updatePhoneResult = userService.updatePhone(1L, "15992321302");
        assertTrue(updatePhoneResult.isSuccess());
    }

    @Test
    public void updateEmail() {
        final Result<UserDTO> updateEmailResult = userService.updateEmail(1L, "   827032783");
        System.out.println(updateEmailResult);
//        assertTrue(updateEmailResult.isSuccess());
    }

    @Test
    public void updatePassword() {
        final Result<UserDTO> updatePasswordResult = userService.updatePassword(7L, "123456");
        assertTrue(updatePasswordResult.isSuccess());
    }

    @Test
    public void disableUser() {
        System.out.println(userService.disableUser(7L));
    }

    @Test
    public void enableUser() {
        System.out.println(userService.enableUser(7L));
    }
}