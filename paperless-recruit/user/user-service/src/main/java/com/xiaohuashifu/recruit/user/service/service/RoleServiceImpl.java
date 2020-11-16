package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.RoleDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 创建角色
     * 角色名必须不存在
     * 如果父角色被禁用了，则该角色也应该被禁用
     *
     * @param roleDTO 需要parentRoleId，roleName，description和available
     * @return Result<RoleDTO>
     */
    @Override
    public Result<RoleDTO> saveRole(RoleDTO roleDTO) {
        // 判断父角色存不存在，父角色必须存在
        RoleDO parentRoleDO = roleMapper.getRole(roleDTO.getParentRoleId());
        if (parentRoleDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This parent role not exists.");
        }

        // 去掉角色名两边的空白符
        roleDTO.setRoleName(roleDTO.getRoleName().trim());

        // 判断角色名存不存在，角色名必须不存在
        int count = roleMapper.countByRoleName(roleDTO.getRoleName());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This role name have been exists.");
        }

        // 如果父角色被禁用了，则该角色也应该被禁用
        if (!parentRoleDO.getAvailable()) {
            roleDTO.setAvailable(false);
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
        roleMapper.saveRole(roleDO);
        return getRole(roleDO.getId());
    }

    /**
     * 删除角色，只允许没有子角色的角色删除
     * 同时会删除该角色所关联的所有权限（Permission）
     *
     * @param id 角色编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> deleteRole(Long id) {
        // 判断该角色存不存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 判断该角色是否还拥有子角色，必须没有子角色才可以删除
        count = roleMapper.countByParentRoleId(id);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This role exists children role.");
        }

        // 删除该角色所关联的权限（Permission）
        roleMapper.deleteRolePermissionByRoleId(id);

        // 删除该角色
        roleMapper.deleteRole(id);
        return Result.success();
    }

    /**
     * 获取角色
     * @param id 角色编号
     * @return Result<RoleDTO>
     */
    @Override
    public Result<RoleDTO> getRole(Long id) {
        final RoleDO role = roleMapper.getRole(id);
        if (role == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(role, RoleDTO.class));
    }

    // TODO: 2020/11/16 也许需要带分页的查询结果
    /**
     * 获取角色
     *
     * @param query 查询参数
     * @return 角色列表
     */
    @Override
    public Result<List<RoleDTO>> getRole(RoleQuery query) {
        List<RoleDO> roleDOList = roleMapper.getRoleByQuery(query);
        return Result.success(roleDOList
                .stream()
                .map(roleDO -> mapper.map(roleDO, RoleDTO.class))
                .collect(Collectors.toList()));
    }

    /**
     * 获取用户角色服务
     * 该服务会根据用户id查询用户的角色，会返回该用户所有角色
     *
     * @param userId 用户id
     * @return 用户的角色列表
     */
    @Override
    public Result<List<RoleDTO>> getRoleListByUserId(Long userId) {
        return Result.success(roleMapper.getRoleListByUserId(userId)
                .stream()
                .map((roleDO -> mapper.map(roleDO, RoleDTO.class)))
                .collect(Collectors.toList()));
    }

    /**
     * 更新角色名，新角色名必须不存在
     *
     * @param id 角色编号
     * @param newRoleName 新角色名
     * @return Result<RoleDTO> 更新后的角色对象
     */
    @Override
    public Result<RoleDTO> updateRoleName(Long id, String newRoleName) {
        // 去除角色名两边空白符
        newRoleName = newRoleName.trim();

        // 判断该角色存不存在，该角色必须存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 判断新角色名存不存在，新角色名必须不存在
        count = roleMapper.countByRoleName(newRoleName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This role name have been exists.");
        }

        // 更新角色名
        roleMapper.updateRoleName(id, newRoleName);
        return getRole(id);
    }

    /**
     * 更新角色描述
     *
     * @param id 角色编号
     * @param newDescription 新角色描述
     * @return Result<RoleDTO> 更新后的角色对象
     */
    @Override
    public Result<RoleDTO> updateDescription(Long id, String newDescription) {
        // 去除角色描述两边空白符
        newDescription = newDescription.trim();

        // 判断该角色存不存在，该角色必须存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 更新角色描述
        roleMapper.updateDescription(id, newDescription);
        return getRole(id);
    }

    /**
     * 禁用角色（且子角色可用状态也被禁用，递归禁用）
     *
     * @param id 角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的key为totalDisableCount和newRole
     */
    @Override
    public Result<Map<String, Object>> disableRole(Long id) {
        // 判断该角色存不存在
        int count = roleMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 判断该角色是否已经被禁用
        count = roleMapper.countByIdAndAvailable(id, false);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This role have bean disable.");
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
     * @param id 角色编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的角色对象，分别对应的key为totalEnableCount和newRole
     */
    @Override
    public Result<Map<String, Object>> enableRole(Long id) {
        // 判断该角色存不存在
        RoleDO roleDO = roleMapper.getRole(id);
        if (roleDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 判断该角色的父角色是否已经被禁用，如果父角色已经被禁用，则无法解禁该角色
        int count = roleMapper.countByIdAndAvailable(roleDO.getParentRoleId(), false);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "Can't enable this role, because the parent role is disable.");
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
     * @param id 角色编号
     * @param parentRoleId 父角色编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的角色对象，分别对应的key为totalDisableCount和newRole
     */
    @Override
    public Result<Map<String, Object>> setParentRole(Long id, Long parentRoleId) {
        // 判断该角色存不存在
        RoleDO roleDO = roleMapper.getRole(id);
        if (roleDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This role not exists.");
        }

        // 如果原来的父角色编号和要设置的父角色编号相同，则直接返回
        if (roleDO.getParentRoleId().equals(parentRoleId)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The parent role has not changed.");
        }

        RoleDO parentRoleDO = null;
        // 若父角色编号不为0，则判断要设置的父亲角色是否存在
        if (parentRoleId != 0) {
            parentRoleDO = roleMapper.getRole(parentRoleId);
            if (parentRoleDO == null) {
                return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This parent role not exists.");
            }
        }

        // 设置父角色
        roleMapper.updateParentRoleId(id, parentRoleId);

        // 如果要设置的父角色编号为0（取消父角色）
        // 或者要设置的父亲角色的状态为可用
        // 或者要设置的父亲角色的状态为禁用且当前角色的状态也为禁用，则直接返回
        if (parentRoleId.equals(0L) || parentRoleDO.getAvailable() || !roleDO.getAvailable()) {
            Map<String, Object> map = new HashMap<>();
            map.put("totalDisableCount", 0);
            map.put("newRole", getRole(id));
            return Result.success(map);
        }

        // 如果父亲角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
        return disableRole(id);
    }

    /**
     * 递归的禁用角色
     *
     * @param id 角色编号
     * @return 总共禁用的角色数量
     */
    private int recursiveDisableRole(Long id) {
        roleMapper.updateAvailable(id, false);
        List<Long> roleIdList = roleMapper.getIdListByParentRoleIdAndAvailable(id, true);
        int count = 1;
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
        List<Long> roleIdList = roleMapper.getIdListByParentRoleId(id);
        for (Long roleId : roleIdList) {
            count += recursiveEnableRole(roleId);
        }
        return count;
    }
}
