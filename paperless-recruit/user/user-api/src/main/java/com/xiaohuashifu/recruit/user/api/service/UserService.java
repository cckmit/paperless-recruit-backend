package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.request.*;

import javax.validation.constraints.*;

/**
 * 描述：用户 RPC 服务公开接口
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public interface UserService {

    /**
     * 注册，对外不使用该方式进行注册
     *
     * @param request CreateUserRequest
     * @return 新创建的用户
     */
    UserDTO register(@NotNull CreateUserRequest request) throws ServiceException;

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名
     * 推荐使用该方式进行注册
     *
     * @param request CreateUserBySmsAuthCodeRequest
     * @return 新创建的用户
     */
    UserDTO registerBySmsAuthCode(@NotNull CreateUserBySmsAuthCodeRequest request) throws ServiceException;

    /**
     * 通过邮箱验证码注册账号
     * 该方式会随机生成用户名
     *
     * @private 内部方法
     *
     * @param request CreateUserByEmailAuthCodeRequest
     * @return 新创建的用户
     */
    UserDTO registerByEmailAuthCode(@NotNull CreateUserByEmailAuthCodeRequest request) throws ServiceException;

    /**
     * 通过id获取用户信息
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    UserDTO getUser(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    UserDTO getUserByUsername(@NotBlank @Username String username) throws NotFoundServiceException;

    /**
     * 通过用户名或者手机或者邮箱获取用户对象
     * 该接口用于通过[用户名|手机号码|邮箱]+密码进行登录的服务
     *
     * @param usernameOrPhoneOrEmail 用户名或者手机或者邮箱
     * @return 获取到的用户
     */
    UserDTO getUserByUsernameOrPhoneOrEmail(@NotBlank String usernameOrPhoneOrEmail) throws NotFoundServiceException;

    /**
     * 通过手机号码获取用户对象
     *
     * @private 内部方法
     *
     * @param phone 手机号
     * @return 获取到的用户
     */
    UserDTO getUserByPhone(@NotBlank @Phone String phone) throws NotFoundServiceException;

    /**
     * 通过邮箱获取用户对象
     *
     * @private 内部方法
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    UserDTO getUserByEmail(@NotBlank @Email String email) throws NotFoundServiceException;

    /**
     * 多参数查询用户信息
     *
     * @param query 查询参数
     * @return QueryResult<UserDTO> 带分页信息的查询结果用户列表，可能返回空列表
     */
    QueryResult<UserDTO> listUsers(@NotNull UserQuery query);

    /**
     * 更新用户名
     *
     * @param id 用户编号
     * @param username 用户名
     * @return 更新后的用户
     */
    UserDTO updateUsername(@NotNull @Positive Long id, @NotBlank @Username String username)
            throws DuplicateServiceException;

    /**
     * 更新手机号码
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    UserDTO updatePhone(@NotNull @Positive Long id, @NotBlank @Phone String newPhone,
                        @NotBlank @AuthCode String authCode) throws ServiceException;

    /**
     * 更新邮箱
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @param authCode 邮箱认证码
     * @return 更新后的用户
     */
    UserDTO updateEmail(@NotNull @Positive Long id, @NotBlank @Email String newEmail,
                        @NotBlank @AuthCode String authCode) throws ServiceException;

    /**
     * 更新密码
     *
     * @param id 用户编号
     * @param password 密码
     * @return 更新后的用户
     */
    UserDTO updatePassword(@NotNull @Positive Long id, @NotEmpty @Password String password);

    /**
     * 更新密码，通过邮箱验证码
     *
     * @param request UpdatePasswordByEmailAuthCodeRequest
     * @return 更新后的用户
     */
    UserDTO updatePasswordByEmailAuthCode(@NotNull UpdatePasswordByEmailAuthCodeRequest request)
            throws ServiceException;

    /**
     * 更新密码，通过短信验证码
     *
     * @param request UpdatePasswordBySmsAuthCodeRequest
     * @return 更新后的用户
     */
    UserDTO updatePasswordBySmsAuthCode(@NotNull UpdatePasswordBySmsAuthCodeRequest request) throws ServiceException;

    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    UserDTO disableUser(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁用户
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    UserDTO enableUser(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 发送注册账号时使用的短信验证码
     *
     * @param phone 手机号码
     */
    void sendSmsAuthCodeForSignUp(@NotBlank @Phone String phone) throws ServiceException;

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @param email 邮箱
     * @param title 邮件的标题
     */
    void sendEmailAuthCodeForSignUp(@NotBlank @Email String email, @NotBlank String title) throws ServiceException;

    /**
     * 发送更新手机号码时使用的短信验证码
     *
     * @param phone 手机号码
     */
    void sendSmsAuthCodeForUpdatePhone(@NotBlank @Phone String phone) throws ServiceException;

    /**
     * 发送更新密码时使用的短信验证码
     *
     * @param phone 手机号码
     */
    void sendSmsAuthCodeForUpdatePassword(@NotBlank @Phone String phone) throws ServiceException;

    /**
     * 发送更新邮箱时使用的邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailAuthCodeForUpdateEmail(@NotBlank @Email String email) throws ServiceException;

    /**
     * 发送更新密码时使用的邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailAuthCodeForUpdatePassword(@NotBlank @Email String email) throws ServiceException;

}
