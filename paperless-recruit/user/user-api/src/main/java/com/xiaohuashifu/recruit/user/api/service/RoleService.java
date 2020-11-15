package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.group.GroupSave;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述：角色服务RPC接口
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 19:42
 */
@Validated
public interface RoleService {
    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    default Result<List<RoleDTO>> getRoleListByUserId(
            @Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.") Long userId) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param roleDTO
     * @return
     */
    default Result<RoleDTO> saveRole(@Validated(GroupSave.class) RoleDTO roleDTO) {
        throw new UnsupportedOperationException();
    }


    default Result<RoleDTO> getRole(Long id) {
        throw new UnsupportedOperationException();
    }
}
