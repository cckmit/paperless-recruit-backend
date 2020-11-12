package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：权限服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
@Validated
public interface PermissionService {

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @param roleIdList 角色id列表
     * @return 角色的权限列表
     */
    default Result<List<PermissionDTO>> getPermissionListByRoleIdList(
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The roleIdList must be not null.") List<Long> roleIdList) {
        throw new UnsupportedOperationException();
    }
}
