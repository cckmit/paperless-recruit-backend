package com.xiaohuashifu.recruit.userservice.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.userapi.dto.UserDTO;
import com.xiaohuashifu.recruit.userapi.query.UserQuery;
import com.xiaohuashifu.recruit.userapi.service.UserService;
import com.xiaohuashifu.recruit.userservice.dao.UserMapper;
import com.xiaohuashifu.recruit.userservice.pojo.do0.UserDO;
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
     * 创建用户，需要用户名密码
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
