package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：角色服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
public interface RoleService {
    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    default Result<List<RoleDTO>> getRoleListByUserId(@Id @NotNull Long userId) {
        throw new UnsupportedOperationException();
    }


    @interface SaveRole{}
    /**
     * 创建角色
     *
     * @param roleDTO 需要parentRoleId，roleName，description和available
     * @return Result<RoleDTO>
     */
    default Result<RoleDTO> saveRole(RoleDTO roleDTO) {
        throw new UnsupportedOperationException();
    }


    /**
     * 获取角色
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    default Result<RoleDTO> getRole(@Id Long id) {
        throw new UnsupportedOperationException();
    }
}
