package com.xiaohuashifu.recruit.user.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.api.query.UserQuery;
import com.xiaohuashifu.recruit.api.service.UserService;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.dao.UserMapper;
import com.xiaohuashifu.recruit.user.pojo.do0.UserDO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 描述：用户服务的rpc实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final Mapper mapper;

    @Autowired
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
    public Result<UserDTO> getUser(@Id Long id) {
        final UserDO user = userMapper.getUser(id);
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
     * @param userDTO 用户对象
     * @return 新创建的用户
     */
    @Override
    public Result<UserDTO> saveUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public Result<UserDTO> updateUser(UserDTO userDTO) {
        return null;
    }
}
