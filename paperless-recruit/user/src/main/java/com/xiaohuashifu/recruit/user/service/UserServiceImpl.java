package com.xiaohuashifu.recruit.user.service;

import com.xiaohuashifu.recruit.api.service.UserService;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import org.apache.dubbo.config.annotation.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getUser(Integer id) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123456");
        userDTO.setUsername("xhsf");
        return userDTO;
    }
}
