package com.xiaohuashifu.recruit.userapi.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;
import com.xiaohuashifu.recruit.userapi.dto.UserDTO;
import com.xiaohuashifu.recruit.userapi.query.UserQuery;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 描述：用户RPC服务公开接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
@Validated
public interface UserService {
    /**
     * 通过id获取用户信息
     * 这里不会附带密码等敏感信息
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    default Result<UserDTO> getUser(
            @Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过用户名获取用户对象
     * 这里不会附带密码等敏感信息
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    default Result<UserDTO> getUserByUsername(
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The username must be not null.") @Username String username) {
        throw new UnsupportedOperationException();
    }

    /**
     * 多参数查询用户信息
     * 这里不会附带密码等敏感信息
     *
     * @param query 查询参数
     * @return 查询结果用户列表
     */
    default Result<List<UserDTO>> getUser(UserQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 创建用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    default Result<UserDTO> saveUser(
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The username must be not null.") @Username String username,
            @NotBlank(message = "INVALID_PARAMETER_IS_NULL: The password must be not null.") @Password String password) {
         throw new UnsupportedOperationException();
    }

    /**
     * 更新用户名
     *
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    default Result<UserDTO> updateUsername(
            @Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.") Long id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The username must be not null.") @Username String newUsername) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新密码
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @return 更新后的用户
     */
    default Result<UserDTO> updatePassword(
            @Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.") Long id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The password must be not null.") @Password String newPassword) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新用户available状态
     *
     * @param id 用户编号
     * @param available 是否有效，true表示有效，false表示无效
     * @return 更新后的用户
     */
    default Result<UserDTO> updateAvailableState(
            @Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.") Long id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The available must be not null.") Boolean available) {
        throw new UnsupportedOperationException();
    }

}
