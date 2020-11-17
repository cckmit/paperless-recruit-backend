package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.PermissionDO;
import com.xiaohuashifu.recruit.user.service.pojo.do0.RoleDO;
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

//    /**
//     * 创建权限
//     *
//     * @param permissionDTO parentPermissionId，permissionName，authorizationUrl，description和available
//     * @return Result<PermissionDTO>
//     */
//    @Override
//    public Result<PermissionDTO> savePermission(PermissionDTO permissionDTO) {
//        // 如果父角色编号不为0，则父角色必须存在
//        if (permissionDTO) {
//            RoleDO parentRoleDO = roleMapper.getRole(roleDTO.getParentRoleId());
//            if (parentRoleDO == null) {
//                return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This parent role not exists.");
//            }
//        }
//
//        // 去掉角色名两边的空白符
//        roleDTO.setRoleName(roleDTO.getRoleName().trim());
//
//        // 判断角色名存不存在，角色名必须不存在
//        int count = roleMapper.countByRoleName(roleDTO.getRoleName());
//        if (count > 0) {
//            return Result.fail(ErrorCode.INVALID_PARAMETER, "This role name have been exists.");
//        }
//
//        // 如果父角色被禁用了，则该角色也应该被禁用
//        if (!parentRoleDO.getAvailable()) {
//            roleDTO.setAvailable(false);
//        }
//
//        // 去掉角色描述两边的空白符
//        roleDTO.setDescription(roleDTO.getDescription().trim());
//
//        // 保存角色
//        RoleDO roleDO = new RoleDO.Builder()
//                .parentRoleId(roleDTO.getParentRoleId())
//                .roleName(roleDTO.getRoleName())
//                .description(roleDTO.getDescription())
//                .available(roleDTO.getAvailable())
//                .build();
//        roleMapper.saveRole(roleDO);
//        return getRole(roleDO.getId());
//    }

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
