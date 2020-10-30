package com.xiaohuashifu.recruit.user.service;

import com.xiaohuashifu.recruit.api.query.UserQuery;
import com.xiaohuashifu.recruit.api.service.UserService;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.dao.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
@org.springframework.stereotype.Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    /**
     * 通过用户编号获取用户对象
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUser(@Id Long id) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123456");
        userDTO.setUsername("xhsf");
        return Result.success(userDTO);
    }

    @Override
    public Result<List<UserDTO>> getUser(UserQuery query) {
        return null;
    }

    @Override
    public Result<UserDTO> saveUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public Result<UserDTO> changeUser(UserDTO userDTO) {
        return null;
    }
}
