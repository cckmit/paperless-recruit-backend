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
     * 创建用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    default Result<UserDTO> saveUser(@NotBlank @Username String username, @NotNull @Password String password) {
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
     * 更新手机号码
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePhone(@NotNull @Positive Long id, @NotBlank @Phone String newPhone) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19 实现，这里还需要一个private方法用于验证authCode
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
     * 更新邮箱
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @return 更新后的用户
     */
    default Result<UserDTO> updateEmail(@NotNull @Positive Long id, @NotBlank @Email String newEmail) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19 实现，这里还需要一个private方法用于验证authCode
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

    // TODO: 2020/11/19 实现，这里还需要一个private方法用于验证authCode
    /**
     * 更新密码，通过邮箱验证码
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @param authCode 邮箱验证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePasswordByEmailAuthCode(@NotNull @Positive Long id,
                                                          @NotNull @Password String newPassword,
                                                          @NotBlank @AuthCode String authCode) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19 实现，这里还需要一个private方法用于验证authCode
    /**
     * 更新密码，通过短信验证码
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePasswordBySmsAuthCode(@NotNull @Positive Long id,
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

    // TODO: 2020/11/19
    /**
     * 发送邮箱验证码以更新邮箱
     *
     * @param email 邮箱
     * @return String 验证码
     */
    default Result<String> createAndSendEmailAuthCodeForUpdateEmail(@NotBlank @Email String email) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19
    /**
     * 发送短信验证码以更新手机号码
     *
     * @param phone 手机号码
     * @return String 验证码
     */
    default Result<String> createAndSendSmsAuthCodeForUpdatePhone(@NotBlank @Phone String phone) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19
    /**
     * 发送邮箱验证码以更新密码
     *
     * @param email 邮箱
     * @return String 验证码
     */
    default Result<String> createAndSendEmailAuthCodeForUpdatePassword(@NotBlank @Email String email) {
        throw new UnsupportedOperationException();
    }

    // TODO: 2020/11/19
    /**
     * 发送短信验证码以更新密码
     *
     * @param phone 手机号码
     * @return String 验证码
     */
    default Result<String> createAndSendSmsAuthCodeForUpdatePassword(@NotBlank @Phone String phone) {
        throw new UnsupportedOperationException();
    }


}
