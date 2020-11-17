package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.PermissionDO;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
     * 创建权限
     * 权限名必须不存在
     * 如果父权限被禁用了，则该权限也会被禁用
     *
     * @param permissionDTO parentPermissionId，permissionName，authorizationUrl，description和available
     * @return Result<PermissionDTO>
     */
    @Override
    public Result<PermissionDTO> savePermission(PermissionDTO permissionDTO) {
        // 如果父权限编号不为0，则父权限必须存在
        if (!permissionDTO.getParentPermissionId().equals(0L)) {
            int count = permissionMapper.count(permissionDTO.getParentPermissionId());
            if (count < 1) {
                return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                        "This parent permission not exists.");
            }
        }

        // 去掉权限名两边的空白符
        permissionDTO.setPermissionName(permissionDTO.getPermissionName().trim());

        // 判断权限名存不存在，权限名必须不存在
        int count = permissionMapper.countByPermissionName(permissionDTO.getPermissionName());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This permission name have been exists.");
        }

        // 如果父权限编号不为0，且被禁用了，则该权限也应该被禁用
        if (!permissionDTO.getParentPermissionId().equals(0L)) {
            count = permissionMapper.countByIdAndAvailable(permissionDTO.getParentPermissionId(), false);
            if (count > 0) {
                permissionDTO.setAvailable(false);
            }
        }

        // 去掉权限描述和授权路径两边的空白符
        permissionDTO.setDescription(permissionDTO.getDescription().trim());
        permissionDTO.setAuthorizationUrl(permissionDTO.getAuthorizationUrl().trim());

        // 保存权限
        PermissionDO permissionDO = new PermissionDO.Builder()
                .parentPermissionId(permissionDTO.getParentPermissionId())
                .permissionName(permissionDTO.getPermissionName())
                .authorizationUrl(permissionDTO.getAuthorizationUrl())
                .description(permissionDTO.getDescription())
                .available(permissionDTO.getAvailable())
                .build();
        permissionMapper.savePermission(permissionDO);
        return getPermission(permissionDO.getId());
    }

    /**
     * 删除权限，只允许没有子权限的权限删除
     * 同时会删除与此权限关联的所有角色（Role）的关联关系
     *
     * @param id 权限编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> deletePermission(Long id) {
        // 判断该权限存不存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 判断该权限是否还拥有子权限，必须没有子权限才可以删除
        count = permissionMapper.countByParentPermissionId(id);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This permission exists children permission.");
        }

        // 删除该权限所关联的角色（Role）的关联关系
        permissionMapper.deleteRolePermissionByPermissionId(id);

        // 删除该权限
        permissionMapper.deletePermission(id);
        return Result.success();
    }

    /**
     * 获取权限
     * @param id 权限编号
     * @return Result<PermissionDTO>
     */
    @Override
    public Result<PermissionDTO> getPermission(Long id) {
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(permissionDO, PermissionDTO.class));
    }

    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    @Override
    public Result<List<PermissionDTO>> getAllPermission() {
        return Result.success(permissionMapper
                .getAllPermission()
                .stream()
                .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                .collect(Collectors.toList()));
    }

    /**
     * 获取角色权限服务
     * 该服务会根据角色id列表查询角色的权限列表，会返回所有角色的权限列表
     *
     * @param roleIdList 角色id列表
     * @return 角色的权限列表
     */
    @Override
    public Result<List<PermissionDTO>> getPermissionByRoleIdList(List<Long> roleIdList) {
        return Result.success(
                permissionMapper
                        .getPermissionListByRoleIdList(roleIdList)
                        .stream()
                        .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                        .collect(Collectors.toList()));
    }

    /**
     * 通过用户id获取用户权限列表
     *
     * @param userId 用户id
     * @return 用户的权限列表
     */
    @Override
    public Result<List<PermissionDTO>> getPermissionByUserId(Long userId) {
        List<PermissionDO> permissionDOList = permissionMapper.getPermissionByUserId(userId);
        return Result.success(permissionDOList
                .stream()
                .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                .collect(Collectors.toList()));
    }


}
