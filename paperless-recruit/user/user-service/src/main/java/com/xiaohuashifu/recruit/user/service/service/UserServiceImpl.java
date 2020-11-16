package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.UserDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 描述：用户服务的rpc实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Reference
    private PasswordService passwordService;

    private final UserMapper userMapper;

    private final Mapper mapper;

    public UserServiceImpl(UserMapper userMapper, Mapper mapper) {
        this.userMapper = userMapper;
        this.mapper = mapper;
    }

    /**
     * 创建用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    @Override
    public Result<UserDTO> saveUser(String username, String password) {
        // 判断用户名是否存在
        int count = userMapper.countUserByUsername(username.trim());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Username exists.");
        }

        // 添加到数据库
        UserDO userDO = new UserDO.Builder()
                .username(username.trim())
                .password(password)
                .build();
        count = userMapper.saveUser(userDO);
        // 添加出错，可能是并发产生的冲突，或者数据库出错
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Save user error, username=" + username.trim() + ".");
        }

        return getUser(userDO.getId());
    }

    /**
     * 通过用户编号获取用户对象
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUser(Long id) {
        final UserDO user = userMapper.getUser(id);
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByUsername(String username) {
        final UserDO user = userMapper.getUserByUsername(username.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过手机号码获取用户对象
     *
     * @param phone 手机号码
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByPhone(String phone) {
        final UserDO user = userMapper.getUserByPhone(phone.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过邮箱获取用户对象
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByEmail(String email) {
        final UserDO user = userMapper.getUserByEmail(email.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 多参数查询用户信息
     *
     * @param query 查询参数
     * @return 查询结果用户列表
     */
    @Override
    public Result<PageInfo<UserDTO>> getUser(UserQuery query) {
        List<UserDO> userDOList = userMapper.getUserByQuery(query);
        List<UserDTO> userDTOList = userDOList
                .stream()
                .map(userDO -> mapper.map(userDO, UserDTO.class))
                .collect(Collectors.toList());
        PageInfo<UserDTO> pageInfo = new PageInfo<>(userDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新用户名
     *
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateUsername(Long id, String newUsername) {
        // 判断用户名是否存在
        int count = userMapper.countUserByUsername(newUsername.trim());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New username exists.");
        }

        // 更新用户名
        count = userMapper.updateUsername(id, newUsername.trim());
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update username error, new username=" + newUsername.trim() + ".");
        }
        return getUser(id);
    }

    /**
     * 更新手机
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePhone(Long id, String newPhone) {
        // 判断手机号码是否存在
        int count = userMapper.countUserByPhone(newPhone.trim());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New phone exists.");
        }

        // 更新手机号码
        count = userMapper.updatePhone(id, newPhone.trim());
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update phone error, new phone=" + newPhone.trim() + ".");
        }
        return getUser(id);
    }

    /**
     * 更新邮箱
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateEmail(Long id, String newEmail) {
        // 判断邮箱是否存在
        int count = userMapper.countUserByEmail(newEmail.trim());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New email exists.");
        }

        // 更新邮箱
        count = userMapper.updateEmail(id, newEmail.trim());
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update email error, new email=" + newEmail.trim() + ".");
        }
        return getUser(id);
    }

    /**
     * 更新密码
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePassword(Long id, String newPassword) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 更新密码
        userMapper.updatePassword(id, passwordService.encodePassword(newPassword));
        return getUser(id);
    }

    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    @Override
    public Result<UserDTO> disableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断用户当前的状态是不是已经是禁用
        if (!userDO.getAvailable()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This user has been disable.");
        }

        // 禁用用户
        userMapper.updateAvailable(id, false);
        return getUser(id);
    }

    /**
     * 解禁用户
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    @Override
    public Result<UserDTO> enableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断用户当前的状态是不是已经是可用
        if (userDO.getAvailable()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This user has been enable.");
        }

        // 解禁用户
        userMapper.updateAvailable(id, true);
        return getUser(id);
    }
}
