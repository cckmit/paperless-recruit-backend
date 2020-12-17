package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;

import javax.validation.constraints.*;

/**
 * 描述：用户 RPC 服务公开接口
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public interface UserService {

    /**
     * 创建用户，对外不使用该方式进行注册
     *
     * @permission 需要 admin 角色
     *
     * @errorCode InvalidParameter: 用户名或密码格式错误
     *              OperationConflict: 用户名已经存在
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    Result<UserDTO> signUpUser(@NotBlank(message = "The username can't be blank.") @Username String username,
                               @NotEmpty(message = "The password can't be empty.") @Password String password);

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名
     * 推荐使用该方式进行注册
     *
     * @errorCode InvalidParameter: 手机号码或验证码或密码格式错误
     *              OperationConflict: 手机号码已经存在
     *              OperationConflict.Lock: 无法获取关于该手机号码的锁
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *
     * @param phone 手机号码
     * @param authCode 短信验证码
     * @param password 密码
     * @return 新创建的用户
     */
    Result<UserDTO> signUpBySmsAuthCode(@NotBlank(message = "The phone can't be blank.") @Phone String phone,
                                        @NotBlank(message = "The authCode can't be blank.") @AuthCode String authCode,
                                        @NotEmpty(message = "The password can't be empty.") @Password String password);

    /**
     * 通过邮箱验证码注册账号
     * 该方式会随机生成用户名
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 邮箱或验证码或密码格式错误
     *              OperationConflict: 邮箱已经存在
     *              OperationConflict.Lock: 无法获取关于该邮箱的锁
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *
     * @param email 邮箱
     * @param authCode 邮箱验证码
     * @param password 密码
     * @return 新创建的用户
     */
    Result<UserDTO> signUpByEmailAuthCode(
            @NotBlank(message = "The email can't be blank.") @Email String email,
            @NotBlank(message = "The authCode can't be blank.") @AuthCode String authCode,
            @NotEmpty(message = "The password can't be empty.") @Password String password);

    /**
     * 通过id获取用户信息
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 编号格式错误
     *              InvalidParameter.NotFound: 该编号的用户不存在
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    Result<UserDTO> getUser(@NotNull(message = "The id can't be null.")
                            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 通过用户名获取用户对象
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 用户名格式错误
     *              InvalidParameter.NotFound: 该用户名的用户不存在
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    Result<UserDTO> getUserByUsername(@NotBlank(message = "The username can't be blank.")
                                      @Username String username);

    /**
     * 通过用户名或者手机或者邮箱获取用户对象
     * 该接口用于通过[用户名|手机号码|邮箱]+密码进行登录的服务
     *
     * @errorCode InvalidParameter: [用户名或者手机或者邮箱]格式错误
     *              InvalidParameter.NotFound: 该[用户名或者手机或者邮箱]对应的用户不存在
     *
     * @param usernameOrPhoneOrEmail 用户名或者手机或者邮箱
     * @return 获取到的用户
     */
    Result<UserDTO> getUserByUsernameOrPhoneOrEmail(@NotBlank(message = "The usernameOrPhoneOrEmail can't be blank.")
                                                            String usernameOrPhoneOrEmail);

    /**
     * 通过手机号码获取用户对象
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              InvalidParameter.NotFound: 该手机号码的用户不存在
     *
     * @param phone 手机号
     * @return 获取到的用户
     */
    Result<UserDTO> getUserByPhone(@NotBlank(message = "The phone can't be blank.") @Phone String phone);

    /**
     * 通过邮箱获取用户对象
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              InvalidParameter.NotFound: 该邮箱的用户不存在
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    Result<UserDTO> getUserByEmail(@NotBlank(message = "The email can't be blank.")
                                   @Email(message = "The email format error.") String email);

    /**
     * 多参数查询用户信息
     *
     * @errorCode InvalidParameter: 查询参数出错
     *
     * @param query 查询参数
     * @return Result<PageInfo<UserDTO>> 带分页信息的查询结果用户列表，可能返回空列表
     */
    Result<PageInfo<UserDTO>> listUsers(@NotNull(message = "The query can't be null.") UserQuery query);

    /**
     * 更新用户名
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 用户编号或新用户名格式错误 | 用户不存在
     *              OperationConflict: 新用户名已经存在
     *              OperationConflict.Lock: 无法获取关于该用户名的锁
     *              Forbidden: 用户被禁用
     *
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    Result<UserDTO> updateUsername(@NotNull(message = "The id can't be null.")
                                   @Positive(message = "The id must be greater than 0.") Long id,
                                   @NotBlank(message = "The newUsername can't be blank.")
                                   @Username String newUsername);

    /**
     * 更新手机号码
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 用户编号或新手机号码或短信验证码格式错误 | 用户不存在
     *              OperationConflict: 新手机号码已经存在
     *              OperationConflict.Lock: 无法获取关于该手机号码的锁
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *              Forbidden: 用户被禁用
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    Result<UserDTO> updatePhone(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPhone can't be blank.")
            @Phone String newPhone,
            @NotBlank(message = "The authCode can't be blank.")
            @AuthCode String authCode);

    /**
     * 更新邮箱
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 用户编号或新邮箱或邮箱验证码格式错误 | 用户不存在
     *              OperationConflict: 新邮箱已经存在
     *              OperationConflict.Lock: 无法获取关于该邮箱的锁
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *              Forbidden: 用户被禁用
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @param authCode 邮箱认证码
     * @return 更新后的用户
     */
    Result<UserDTO> updateEmail(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newEmail can't be blank.")
            @Email(message = "The newEmail format error.") String newEmail,
            @NotBlank(message = "The authCode can't be blank.")
            @AuthCode String authCode);

    /**
     * 更新密码
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 用户编号或新密码格式错误 | 该用户不存在
     *              Forbidden: 用户被禁用
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @return 更新后的用户
     */
    Result<UserDTO> updatePassword(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotEmpty(message = "The newPassword can't be empty.")
            @Password String newPassword);

    /**
     * 更新密码，通过邮箱验证码
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该邮箱的用户不存在
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *              Forbidden: 用户被禁用
     *
     * @param email 邮箱
     * @param newPassword 新密码
     * @param authCode 邮箱验证码
     * @return 更新后的用户
     */
    Result<UserDTO> updatePasswordByEmailAuthCode(
            @NotBlank(message = "The email can't be blank.")
            @Email(message = "The email format error.") String email,
            @NotEmpty(message = "The newPassword can't be empty.")
            @Password String newPassword,
            @NotBlank(message = "The authCode can't be blank.")
            @AuthCode String authCode);

    /**
     * 更新密码，通过短信验证码
     *
     * @permission 必须是用户自身
     *
     * @errorCode InvalidParameter: 手机号码或验证码或新密码格式错误
     *              InvalidParameter.NotFound: 对应手机号码的用户不存在
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *              Forbidden: 用户被禁用
     *
     * @param phone 手机号码
     * @param newPassword 新密码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    Result<UserDTO> updatePasswordBySmsAuthCode(
            @NotBlank(message = "The phone can't be blank.")
            @Phone String phone,
            @NotEmpty(message = "The newPassword can't be empty.")
            @Password String newPassword,
            @NotBlank(message = "The authCode can't be blank.")
            @AuthCode String authCode);

    /**
     * 禁用用户
     *
     * @permission 必须 admin 权限
     *
     * @errorCode InvalidParameter: 用户编号格式错误 | 用户不存在
     *              OperationConflict: 用户已经被禁用，无需再次禁用
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    Result<UserDTO> disableUser(@NotNull(message = "The id can't be null.")
                                @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁用户
     *
     * @permission 必须 admin 权限
     *
     * @errorCode InvalidParameter: 用户编号格式错误 | 用户不存在
     *              OperationConflict: 用户没有被禁用，无需解禁
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    Result<UserDTO> enableUser(@NotNull(message = "The id can't be null.")
                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 判断用户是否存在
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 用户编号格式错误
     *              InvalidParameter.User.NotExist: 对应编号的用户不存在
     *
     * @param id 用户编号
     * @return 是否存在，Result.success=true表示用户存在
     */
    <T> Result<T> userExists(@NotNull(message = "The id can't be null.")
                             @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 检查用户状态
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 用户编号格式错误
     *              InvalidParameter.User.NotExist: 用户不存在
     *              Forbidden.User: 用户不可用
     *
     * @param id 用户编号
     * @return 检查结果
     */
    <T> Result<T> checkUserStatus(@NotNull(message = "The id can't be null.")
                                  @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 发送注册账号时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              OperationConflict: 该手机号码已经被注册，无法发送验证码
     *              UnknownError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    Result<Void> sendSmsAuthCodeForSignUp(@NotBlank(message = "The phone can't be blank.")
                                          @Phone String phone);

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱或标题格式错误
     *              OperationConflict: 该邮箱已经被注册，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param email 邮箱
     * @param title 邮件的标题
     * @return 发送结果
     */
    Result<Void> sendEmailAuthCodeForSignUp(@NotBlank(message = "The email can't be blank.")
                                            @Email(message = "The email format error.") String email,
                                            @NotBlank(message = "The title can't be blank.") String title);

    /**
     * 发送更新手机号码时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              OperationConflict: 该手机号码已经被使用，无法发送验证码
     *              UnknownError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    Result<Void> sendSmsAuthCodeForUpdatePhone(@NotBlank(message = "The phone can't be blank.")
                                               @Phone String phone);

    /**
     * 发送更新密码时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              InvalidParameter.NotFound: 手机号码不存在，不给发送验证码
     *              UnknownError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    Result<Void> sendSmsAuthCodeForUpdatePassword(@NotBlank(message = "The phone can't be blank.")
                                                  @Phone String phone);

    /**
     * 发送更新邮箱时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              OperationConflict: 该邮箱已经被使用，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    Result<Void> sendEmailAuthCodeForUpdateEmail(@NotBlank(message = "The email can't be blank.")
                                                 @Email String email);

    /**
     * 发送更新密码时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              InvalidParameter.NotFound: 邮箱地址不存在，不给发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    Result<Void> sendEmailAuthCodeForUpdatePassword(@NotBlank(message = "The email can't be blank.")
                                                    @Email String email);

}
