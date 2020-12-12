package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.UserService");
        reference.setApplication(application);
        reference.setInterface(UserService.class);
        reference.setTimeout(10000000);
        userService = reference.get();
    }

    @Test
    public void getUser() {
//        final Result<UserDTO> user = userService.getUser(1L);
//        System.out.println(user);
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(6L);
        System.out.println(userService.listUsers(new UserQuery.Builder().pageNum(1L).pageSize(50L).ids(ids).build()));
    }

    @Test
    public void signUpUser() {
        final Result<UserDTO> saveUserResult = userService.signUpUser(
                "profiletest", "123456");
        System.out.println(saveUserResult);
    }

    @Test
    public void signUpBySmsAuthCode() {
        System.out.println(userService.signUpBySmsAuthCode(
                "15992321303", "989575", "123456"));
    }

    @Test
    public void signUpByEmailAuthCode() {
        System.out.println(userService.signUpByEmailAuthCode(
                "827032783@qq.com", "075478", "123456"));
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
    public void getUserByUsernameOrPhoneOrEmail() {
        System.out.println(userService.getUserByUsernameOrPhoneOrEmail("8270323783@qq.com"));
    }

    @Test
    public void getUserByPhone() {
        System.out.println(userService.getUserByPhone(null));
    }

    @Test
    public void getUserByEmail() {
        System.out.println(userService.getUserByEmail("827032782"));
    }

    @Test
    public void updateUsername() {
        System.out.println(userService.updateUsername(12L, "xhsfnew4"));
    }

    @Test
    public void updatePhone() {
        Result<UserDTO> updatePhoneResult =
                userService.updatePhone(1L, "15992321303", "214481");
        System.out.println(updatePhoneResult);
    }

    @Test
    public void updateEmail() {
        Result<UserDTO> updateEmailResult = userService.updateEmail(1L, "827032783@qq.com", "495483");
        System.out.println(updateEmailResult);
//        assertTrue(updateEmailResult.isSuccess());
    }

    @Test
    public void updatePassword() {
        Result<UserDTO> updatePasswordResult = userService.updatePassword(7L, "123456");
        assertTrue(updatePasswordResult.isSuccess());
    }


    @Test
    public void updatePasswordByEmailAuthCode() {
        Result<UserDTO> updatePasswordResult = userService.updatePasswordByEmailAuthCode(
                "827032783@qq.com", "123456", "672394");
        System.out.println(updatePasswordResult.isSuccess());
    }


    @Test
    public void updatePasswordBySmsAuthCode() {
        Result<UserDTO> updatePasswordResult = userService.updatePasswordBySmsAuthCode(
                "15992321303", "123456", "223231");
        System.out.println(updatePasswordResult.isSuccess());
    }

    @Test
    public void disableUser() {
        System.out.println(userService.disableUser(2L));
    }

    @Test
    public void enableUser() {
        System.out.println(userService.enableUser(7L));
    }

    @Test
    public void userExists() {
        System.out.println(userService.userExists(16L));
    }

    @Test
    public void sendSmsAuthCodeForSignUp() {
        System.out.println(userService.sendSmsAuthCodeForSignUp("15992321303"));
    }

    @Test
    public void sendSmsAuthCodeForUpdatePhone() {
        System.out.println(userService.sendSmsAuthCodeForUpdatePhone("15992321309"));
    }

    @Test
    public void sendSmsAuthCodeForUpdatePassword() {
        System.out.println(userService.sendSmsAuthCodeForUpdatePassword("15992321303"));
    }

    @Test
    public void sendEmailAuthCodeForUpdateEmail() {
        System.out.println(userService.sendEmailAuthCodeForUpdateEmail("827032783@qq.com"));
    }

    @Test
    public void sendEmailAuthCodeForUpdatePassword() {
        System.out.println(userService.sendEmailAuthCodeForUpdatePassword("827032783@qq.com"));
    }

    @Test
    public void sendEmailAuthCodeForSignUp() {
        System.out.println(userService.sendEmailAuthCodeForSignUp("827032783@qq.com"));
    }
}