package com.xiaohuashifu.recruit.api.service;

import com.xiaohuashifu.recruit.api.query.UserQuery;
import com.xiaohuashifu.recruit.common.pojo.dto.UserDTO;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;

import java.util.List;

/**
 * 描述：用户RPC服务公开接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public interface UserService {
    /**
     * 通过id获取用户信息
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    default Result<UserDTO> getUser(@Id Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 多参数查询用户信息
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
     * @param userDTO 用户对象
     * @return 新创建的用户
     */
    default Result<UserDTO> saveUser(UserDTO userDTO) {
         throw new UnsupportedOperationException();
    }

    /**
     * 更新用户信息
     *
     * @param userDTO 要更新的用户信息，如果不为null则更新
     * @return 更新后的用户
     */
    default Result<UserDTO> changeUser(UserDTO userDTO) {
        throw new UnsupportedOperationException();
    }

}
