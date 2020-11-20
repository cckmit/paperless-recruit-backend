package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


/**
 * 描述：用户RPC服务公开接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public interface UserService {

    /**
     * 创建用户，对外不使用该方式进行注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    default Result<UserDTO> signUpUser(@NotBlank @Username String username, @NotNull @Password String password) {
        throw new UnsupportedOperationException();
    }

    /**
     * 验证码注册的主题，用于调用短信验证码服务
     */
    String SIGN_UP_SUBJECT = "user:sign_up";

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名和密码
     *
     * @param phone 手机号码
     * @param authCode 短信验证码
     * @return 新创建的用户
     */
    default Result<UserDTO> signUpBySmsAuthCode(@NotBlank @Phone String phone,
                                                  @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过id获取用户信息
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    default Result<UserDTO> getUser(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    default Result<UserDTO> getUserByUsername(@NotBlank @Username String username) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过用户名或者手机或者邮箱获取用户对象
     * 该接口用于通过[用户名|手机号码|邮箱]+密码进行登录的服务
     *
     * @param usernameOrPhoneOrEmail 用户名或者手机或者邮箱
     * @return 获取到的用户
     */
    default Result<UserDTO> getUserByUsernameOrPhoneOrEmail(@NotBlank String usernameOrPhoneOrEmail) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过手机号码获取用户对象
     *
     * @param phone 手机号
     * @return 获取到的用户
     */
    default Result<UserDTO> getUserByPhone(@NotBlank @Phone String phone) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过邮箱获取用户对象
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    default Result<UserDTO> getUserByEmail(@NotBlank @Email String email) {
        throw new UnsupportedOperationException();
    }

    /**
     * 多参数查询用户信息
     *
     * @param query 查询参数
     * @return Result<PageInfo<UserDTO>> 带分页信息的查询结果用户列表
     */
    default Result<PageInfo<UserDTO>> getUser(@NotNull UserQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新用户名
     *
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    default Result<UserDTO> updateUsername(@NotNull @Positive Long id, @NotBlank @Username String newUsername) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新手机号码的主题，用于调用短信验证码服务
     */
    String UPDATE_PHONE_SUBJECT = "user:update-phone";

    /**
     * 更新手机号码
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePhone(@NotNull @Positive Long id, @NotBlank @Phone String newPhone,
                                        @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新邮件的主题，用于调用邮箱验证码服务
     */
    String UPDATE_EMAIL_SUBJECT = "user:update-email";

    /**
     * 更新邮箱
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @param authCode 邮箱认证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updateEmail(@NotNull @Positive Long id, @NotBlank @Email String newEmail,
                                        @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新密码
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePassword(@NotNull @Positive Long id, @NotNull @Password String newPassword) {
        throw new UnsupportedOperationException();
    }


    /**
     * 邮箱验证码更新密码的主题，用于调用邮箱验证码服务
     */
    String UPDATE_PASSWORD_BY_EMAIL_AUTH_CODE_SUBJECT = "user:update-password:email-auth-code";

    /**
     * 更新密码，通过邮箱验证码
     *
     * @param email 邮箱
     * @param newPassword 新密码
     * @param authCode 邮箱验证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePasswordByEmailAuthCode(@NotBlank @Email String email,
                                                          @NotNull @Password String newPassword,
                                                          @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    /**
     * 短信验证码更新密码的主题，用于调用短信验证码服务
     */
    String UPDATE_PASSWORD_BY_SMS_AUTH_CODE_SUBJECT = "user:update-password:sms-auth-code";

    /**
     * 更新密码，通过短信验证码
     *
     * @param phone 手机号码
     * @param newPassword 新密码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePasswordBySmsAuthCode(@NotBlank @Phone String phone,
                                                          @NotNull @Password String newPassword,
                                                          @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    default Result<UserDTO> disableUser(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 解禁用户
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    default Result<UserDTO> enableUser(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 判断用户是否存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    default Result<Void> userExists(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

}
