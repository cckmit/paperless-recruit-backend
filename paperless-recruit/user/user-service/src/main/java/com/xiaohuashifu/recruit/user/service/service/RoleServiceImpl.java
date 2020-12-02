package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述：角色服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;
    private final Mapper mapper;

    public RoleServiceImpl(RoleMapper roleMapper, UserMapper userMapper,
                           PermissionMapper permissionMapper, Mapper mapper) {
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
        this.mapper = mapper;
    }

    /**
     * 创建角色
     * 角色名必须不存在
     * 如果父角色被禁用了，则该角色也会被禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 父角色不存在
     *              OperationConflict: 角色名已经存在
     *
     * @param roleDTO 需要parentRoleId，roleName，description和available
     * @return Result<RoleDTO>
     */
    @Override
    public Result<RoleDTO> saveRole(RoleDTO roleDTO) {
        // 如果父角色编号不为0，则父角色必须存在
        if (!roleDTO.getParentRoleId().equals(0L)) {
            int count = roleMapper.count(roleDTO.getParentRoleId());
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The parent does not exist.");
            }
        }

        // 去掉角色名两边的空白符
        roleDTO.setRoleName(roleDTO.getRoleName().trim());

        // 判断角色名存不存在，角色名必须不存在
        int count = roleMapper.countByRoleName(roleDTO.getRoleName());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role name already exist.");
        }

        // 如果父角色编号不为0，且被禁用了，则该角色也应该被禁用
        if (!roleDTO.getParentRoleId().equals(0L)) {
            count = roleMapper.countByIdAndAvailable(roleDTO.getParentRoleId(), false);
            if (count > 0) {
                roleDTO.setAvailable(false);
            }
        }

        // 去掉角色描述两边的空白符
        roleDTO.setDescription(roleDTO.getDescription().trim());

        // 保存角色
        RoleDO roleDO = new RoleDO.Builder()
                .parentRoleId(roleDTO.getParentRoleId())
                .roleName(roleDTO.getRoleName())
                .description(roleDTO.getDescription())
                .available(roleDTO.getAvailable())
                .build();
        roleMapper.insertRole(roleDO);
        return getRole(roleDO.getId());
    }

    /**
     * 创建用户角色，也就是给用户绑定角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 角色不存在
     *              OperationConflict: 用户已经有该角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> saveUserRole(Long userId, Long roleId) {
        // 判断该用户存不存在
        int count = userMapper.count(userId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断该角色存不存在
        count = roleMapper.count(roleId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该用户角色存不存在
        count = roleMapper.countUserRoleByUserIdAndRoleId(userId, roleId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The user already has this role.");
        }

        // 绑定用户与角色
        roleMapper.insertUserRole(userId, roleId);
        return Result.success();
    }

    /**
     * 创建角色权限，也就是给角色绑定权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 权限不存在
     *              OperationConflict: 该角色已经有该权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> saveRolePermission(Long roleId, Long permissionId) {
        // 判断该角色存不存在
        int count = roleMapper.count(roleId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该权限存不存在
        count = permissionMapper.count(permissionId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 判断该角色权限存不存在
        count = roleMapper.countRolePermissionByRoleIdAndPermissionId(roleId, permissionId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role already has this permission.");
        }

        // 绑定角色权限
        roleMapper.insertRolePermission(roleId, permissionId);
        return Result.success();
    }

    /**
     * 删除角色，只允许没有子角色的角色删除
     * 同时会删除此角色拥有的所有权限（Permission）的关联关系
     * 和拥有此角色的用户之间的关联关系
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 该角色存在子角色
     *
     * @param id 角色编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> removeRole(Long id) {
        // 判断该角色存不存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该角色是否还拥有子角色，必须没有子角色才可以删除
        count = roleMapper.countByParentRoleId(id);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role exists children.");
        }

        // 删除该角色所关联的权限（Permission）的关联关系
        roleMapper.deleteRolePermissionByRoleId(id);

        // 删除该角色关联的用户的关联关系
        roleMapper.deleteUserRoleByRoleId(id);

        // 删除该角色
        roleMapper.deleteRole(id);
        return Result.success();
    }

    /**
     * 删除用户绑定的角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 用户不存在 | 角色不存在 | 用户不存在该角色
     *
     * @param userId 用户编号
     * @param roleId 角色编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> removeUserRole(Long userId, Long roleId) {
        // 判断该用户存不存在
        int count = userMapper.count(userId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断该角色存不存在
        count = roleMapper.count(roleId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该用户角色存不存在
        count = roleMapper.countUserRoleByUserIdAndRoleId(userId, roleId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not have this role.");
        }

        // 删除用户角色
        roleMapper.deleteUserRoleByUserIdAndRoleId(userId, roleId);
        return Result.success();
    }

    /**
     * 删除角色绑定的权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 权限不存在 | 角色不存在该权限
     *
     * @param roleId 角色编号
     * @param permissionId 权限编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> removeRolePermission(Long roleId, Long permissionId) {
        // 判断该角色存不存在
        int count = roleMapper.count(roleId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该权限存不存在
        count = permissionMapper.count(permissionId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "This permission does not exist.");
        }

        // 判断该角色权限存不存在
        count = roleMapper.countRolePermissionByRoleIdAndPermissionId(roleId, permissionId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not have this permission.");
        }

        // 删除角色权限
        roleMapper.deleteRolePermissionByRoleIdAndPermissionId(roleId, permissionId);
        return Result.success();
    }

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的角色不存在
     *
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    @Override
    public Result<RoleDTO> getRole(Long id) {
        RoleDO role = roleMapper.getRole(id);
        if (role == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(role, RoleDTO.class));
    }

    /**
     * 获取角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<RoleDTO>> 带分页信息的角色列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<RoleDTO>> listRoles(RoleQuery query) {
        List<RoleDO> roleDOList = roleMapper.listRoles(query);
        List<RoleDTO> roleDTOList = roleDOList
                .stream()
                .map(roleDO -> mapper.map(roleDO, RoleDTO.class))
                .collect(Collectors.toList());
        PageInfo<RoleDTO> pageInfo = new PageInfo<>(roleDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @return 用户的角色列表，可能返回空列表
     */
    @Override
    public Result<List<RoleDTO>> getRoleListByUserId(Long userId) {
        return Result.success(roleMapper.listRolesByUserId(userId)
                .stream()
                .map((roleDO -> mapper.map(roleDO, RoleDTO.class)))
                .collect(Collectors.toList()));
    }

    /**
     * 更新角色名，新角色名必须不存在
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 新角色名已经存在
     *
     * @param id 角色编号
     * @param newRoleName 新角色名
     * @return Result<RoleDTO> 更新后的角色对象
     */
    @Override
    public Result<RoleDTO> updateRoleName(Long id, String newRoleName) {
        // 判断该角色存不存在，该角色必须存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 去除角色名两边空白符
        newRoleName = newRoleName.trim();

        // 判断新角色名存不存在，新角色名必须不存在
        count = roleMapper.countByRoleName(newRoleName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role name already exist.");
        }

        // 更新角色名
        roleMapper.updateRoleName(id, newRoleName);
        return getRole(id);
    }

    /**
     * 更新角色描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *
     * @param id 角色编号
     * @param newDescription 新角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    @Override
    public Result<RoleDTO> updateDescription(Long id, String newDescription) {
        // 判断该角色存不存在，该角色必须存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 去除角色描述两边空白符
        newDescription = newDescription.trim();

        // 更新角色描述
        roleMapper.updateDescription(id, newDescription);
        return getRole(id);
    }

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经被禁用
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的 key 为 totalDisableCount 和 newRole
     */
    @Override
    public Result<Map<String, Object>> disableRole(Long id) {
        // 判断该角色存不存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 判断该角色是否已经被禁用
        count = roleMapper.countByIdAndAvailable(id, false);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role already disable.");
        }

        // 递归的禁用角色
        int totalDisableCount = recursiveDisableRole(id);
        Map<String, Object> map = new HashMap<>();
        map.put("totalDisableCount", totalDisableCount);
        map.put("newRole", getRole(id));
        return Result.success(map);
    }

    /**
     * 解禁角色（且子角色可用状态也被解禁，递归解禁）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在
     *              OperationConflict: 角色已经可用 | 父角色被禁用，无法解禁该角色
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的角色对象，分别对应的key为totalEnableCount和newRole
     */
    @Override
    public Result<Map<String, Object>> enableRole(Long id) {
        // 判断该角色存不存在
        RoleDO roleDO = roleMapper.getRole(id);
        if (roleDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 不能解禁已经有效的角色
        if (roleDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The role is available.");
        }

        // 如果该角色的父角色编号不为0
        // 判断该角色的父角色是否已经被禁用，如果父角色已经被禁用，则无法解禁该角色
        if (!roleDO.getParentRoleId().equals(0L)) {
            int count = roleMapper.countByIdAndAvailable(roleDO.getParentRoleId(), false);
            if (count > 0) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                        "Can't enable this role, because the parent role is disable.");
            }
        }

        // 递归的解禁角色
        int totalEnableCount = recursiveEnableRole(id);
        Map<String, Object> map = new HashMap<>();
        map.put("totalEnableCount", totalEnableCount);
        map.put("newRole", getRole(id));
        return Result.success(map);
    }

    /**
     * 设置父角色
     * 设置parentRoleId为0表示取消父角色设置
     * 如果父亲角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 角色不存在 | 父角色不存在
     *              OperationConflict: 新旧父角色不能相同
     *
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的key为totalDisableCount和newRole
     */
    @Override
    public Result<Map<String, Object>> setParentRole(Long id, Long parentRoleId) {
        // 判断该角色存不存在
        RoleDO roleDO = roleMapper.getRole(id);
        if (roleDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The role does not exist.");
        }

        // 如果原来的父角色编号和要设置的父角色编号相同，则直接返回
        if (roleDO.getParentRoleId().equals(parentRoleId)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The new parent can't same as old parent.");
        }

        // 若父角色编号不为0，则判断要设置的父角色是否存在
        if (!parentRoleId.equals(0L)) {
            int count = roleMapper.count(parentRoleId);
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The parent role does not exist.");
            }
        }

        // 设置父角色
        roleMapper.updateParentRoleId(id, parentRoleId);

        // 如果要设置的父角色编号为0（取消父角色）
        // 或者要设置的父角色的状态为可用
        // 或者要设置的父角色的状态为禁用且当前角色的状态也为禁用，则直接返回
        if (parentRoleId.equals(0L) || roleMapper.countByIdAndAvailable(parentRoleId, true) == 1
                || !roleDO.getAvailable()) {
            Map<String, Object> map = new HashMap<>();
            map.put("totalDisableCount", 0);
            map.put("newRole", getRole(id));
            return Result.success(map);
        }

        // 如果父角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
        return disableRole(id);
    }

    /**
     * 递归的禁用角色
     *
     * @param id 角色编号
     * @return 总共禁用的角色数量
     */
    private int recursiveDisableRole(Long id) {
        int count = roleMapper.updateAvailable(id, false);
        List<Long> roleIdList = roleMapper.listIdsByParentRoleIdAndAvailable(id, true);
        for (Long roleId : roleIdList) {
            count += recursiveDisableRole(roleId);
        }
        return count;
    }

    /**
     * 递归的解禁角色
     *
     * @param id 角色编号
     * @return 总共解禁的角色数量
     */
    private int recursiveEnableRole(Long id) {
        int count = roleMapper.updateAvailableIfUnavailable(id);
        List<Long> roleIdList = roleMapper.listIdsByParentRoleId(id);
        for (Long roleId : roleIdList) {
            count += recursiveEnableRole(roleId);
        }
        return count;
    }
}
