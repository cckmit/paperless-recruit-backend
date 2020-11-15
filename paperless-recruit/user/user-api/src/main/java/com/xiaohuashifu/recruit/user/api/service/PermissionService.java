package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：权限服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public interface PermissionService {

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @param roleIdList 角色id列表
     * @return 角色的权限列表
     */
    default Result<List<PermissionDTO>> getPermissionByRoleIdList(@NotEmpty List<Long> roleIdList) {
        throw new UnsupportedOperationException();
    }


    /**
     * 通过用户id获取用户权限列表
     *
     * @param userId 用户id
     * @return 用户的权限列表
     */
    default Result<List<PermissionDTO>> getPermissionByUserId(@NotNull @Id Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询权限
     *
     * @return 权限列表
     */
    default Result<List<PermissionDTO>> getPermission(@NotNull PermissionQuery query) {
        throw new UnsupportedOperationException();
    }
}
