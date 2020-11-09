package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.UserDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;


/**
 * 描述：用户服务的rpc实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final Mapper mapper;

    public UserServiceImpl(UserMapper userMapper, Mapper mapper) {
        this.userMapper = userMapper;
        this.mapper = mapper;
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
        final UserDO user = userMapper.getUserByUsername(username);
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }


    @Override
    public Result<List<UserDTO>> getUser(UserQuery query) {
        return null;
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
        int count = userMapper.countUserByUsername(username);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Username exists.");
        }

        // 添加到数据库
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(password);
        count = userMapper.saveUser(userDO);
        // 添加出错，可能是并发产生的冲突，或者数据库出错
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Save user error, username=" + username + ".");
        }

        return getUser(userDO.getId());
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
        int count = userMapper.countUserByUsername(newUsername);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New username exists.");
        }

        // 更新用户名
        count = userMapper.updateUsername(id, newUsername);
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update username error, new username=" + newUsername + ".");
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
        // 更新密码
        int count = userMapper.updatePassword(id, newPassword);
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update password error, new username=" + newPassword + ".");
        }
        return getUser(id);
    }

    /**
     * 更新用户available状态
     *
     * @param id 用户编号
     * @param available 是否有效，true表示有效，false表示无效
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateAvailableState(Long id, Boolean available) {
        final UserDO user = userMapper.getUser(id);
        if (user.getAvailable().equals(available)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "Update available state false, the state of available has been is " + available + ".");
        }

        // 更新用户available状态
        int count = userMapper.updateAvailable(id, available);
        if (count < 1) {
            return Result.fail(ErrorCode.INTERNAL_ERROR,
                    "Update available state false, new available=" + available + ".");
        }
        return getUser(id);
    }
}
