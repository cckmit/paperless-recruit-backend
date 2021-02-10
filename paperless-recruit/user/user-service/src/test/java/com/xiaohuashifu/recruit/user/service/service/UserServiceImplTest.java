package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.request.*;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

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
        System.out.println(userService.listUsers(UserQuery.builder().pageNum(3L).pageSize(10L).username("x").build()));
    }

    @Test
    public void signUpUser() {
        UserDTO userDTO = userService.register(
                CreateUserRequest.builder().username("profiletest10").password("123456").build());
        System.out.println(userDTO);
    }

    @Test
    public void signUpBySmsAuthCode() {
        System.out.println(userService.registerBySmsAuthCode(CreateUserBySmsAuthCodeRequest.builder()
                .phone("15992321303").authCode("565209").password("123456").build()));
    }

    @Test
    public void signUpByEmailAuthCode() {
        System.out.println(userService.registerByEmailAuthCode(CreateUserByEmailAuthCodeRequest.builder()
                .email("827032783@qq.com").password("123456").authCode("075478").build()));
    }


    @Test
    public void getUserByUsername() {
        // 正确
        System.out.println(userService.getUserByUsername("xiaohuashifu"));
    }


    @Test
    public void getUserByUsernameOrPhoneOrEmail() {
        System.out.println(userService.getUserByUsernameOrPhoneOrEmail("827032781@qq.com"));
        System.out.println(userService.getUserByUsernameOrPhoneOrEmail("15992321303"));
        System.out.println(userService.getUserByUsernameOrPhoneOrEmail("xiaohuashifu"));
    }

    @Test
    public void getUserByPhone() {
        System.out.println(userService.getUserByPhone("15992321303"));
    }

    @Test
    public void getUserByEmail() {
        System.out.println(userService.getUserByEmail("827032781@qq.com"));
    }

    @Test
    public void updateUsername() {
        System.out.println(userService.updateUsername(12L, "xhsfnew4"));
    }

    @Test
    public void updatePhone() {
        UserDTO userDTO = userService.updatePhone(1L, "15992321303", "214481");
        System.out.println(userDTO);
    }

    @Test
    public void updateEmail() {
        UserDTO userDTO = userService.updateEmail(1L, "827032783@qq.com", "495483");
        System.out.println(userDTO);
    }

    @Test
    public void updatePassword() {
        UserDTO userDTO = userService.updatePassword(7L, "123456");
        System.out.println(userDTO);
    }

    @Test
    public void updatePasswordByEmailAuthCode() {
        UserDTO userDTO = userService.updatePasswordByEmailAuthCode(UpdatePasswordByEmailAuthCodeRequest.builder()
                .email("827032783@qq.com").password("123456").authCode("223231").build());
        System.out.println(userDTO);
    }

    @Test
    public void updatePasswordBySmsAuthCode() {
        UserDTO userDTO = userService.updatePasswordBySmsAuthCode(UpdatePasswordBySmsAuthCodeRequest.builder()
                .phone("15992321303").password("123456").authCode("223231").build());
        System.out.println(userDTO);
    }

    @Test
    public void disableUser() {
        System.out.println(userService.disableUser(2L));
    }

    @Test
    public void enableUser() {
        System.out.println(userService.enableUser(2L));
    }

    @Test
    public void sendSmsAuthCodeForSignUp() {
        userService.sendSmsAuthCodeForSignUp("15992321303");
    }

    @Test
    public void sendSmsAuthCodeForUpdatePhone() {
        userService.sendSmsAuthCodeForUpdatePhone("15992321303");
    }

    @Test
    public void sendSmsAuthCodeForUpdatePassword() {
        userService.sendSmsAuthCodeForUpdatePassword("15992321303");
    }

    @Test
    public void sendEmailAuthCodeForUpdateEmail() {
        userService.sendEmailAuthCodeForUpdateEmail("827032783@qq.com");
    }

    @Test
    public void sendEmailAuthCodeForUpdatePassword() {
        userService.sendEmailAuthCodeForUpdatePassword("827032783@qq.com");
    }

    @Test
    public void sendEmailAuthCodeForSignUp() {
        userService.sendEmailAuthCodeForSignUp("827032783@qq.com", "注册账号");
    }

}