package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:46
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;
    private final Mapper mapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper, Mapper mapper) {
        this.permissionMapper = permissionMapper;
        this.mapper = mapper;
    }

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @param roleIdList 角色id列表
     * @return 角色的权限列表
     */
    @Override
    public Result<List<PermissionDTO>> getPermissionListByRoleIdList(@NotNull(message = "INVALID_PARAMETER_IS_NULL: The roleIdList must be not null.") List<Long> roleIdList) {
        return Result.success(
                permissionMapper
                        .getPermissionListByRoleIdList(roleIdList)
                        .stream()
                        .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                        .collect(Collectors.toList()));
    }
}
