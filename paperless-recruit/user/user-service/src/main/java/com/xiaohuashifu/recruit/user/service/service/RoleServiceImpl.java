package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:40
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final Mapper mapper;

    public RoleServiceImpl(RoleMapper roleMapper, Mapper mapper) {
        this.roleMapper = roleMapper;
        this.mapper = mapper;
    }

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    @Override
    public Result<List<RoleDTO>> getRoleListByUserId(@Id @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.") Long userId) {
        return Result.success(
                roleMapper
                        .getRoleListByUserId(userId)
                        .stream()
                        .map((roleDO -> mapper.map(roleDO, RoleDTO.class)))
                        .collect(Collectors.toList()));
    }
}
