package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private final RoleMapper roleMapper;
    private final Mapper mapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper, RoleMapper roleMapper, Mapper mapper) {
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
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
     * 获取权限
     *
     * @param query 查询参数
     * @return Result<PageInfo<PermissionDTO>> 带分页信息的权限列表
     */
    @Override
    public Result<PageInfo<PermissionDTO>> getPermission(PermissionQuery query) {
        List<PermissionDO> permissionDOList = permissionMapper.getPermissionByQuery(query);
        List<PermissionDTO> permissionDTOList = permissionDOList
                .stream()
                .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                .collect(Collectors.toList());
        PageInfo<PermissionDTO> pageInfo = new PageInfo<>(permissionDTOList);
        return Result.success(pageInfo);
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

    /**
     * 通过用户id获取用户权限（Authority）列表
     * 该权限代表的是权限字符串，而不是Permission对象
     * 主要用于Spring Security框架鉴权使用
     * 包含角色和权限
     * 角色的转换格式为：ROLE_{role_name}
     * 权限的转换格式为：{permission_name}
     *
     * @param userId 用户id
     * @return 用户的权限（Authority）列表
     */
    @Override
    public Result<Set<String>> getAuthorityByUserId(Long userId) {
        // 下面将直接复用这个Set，不再构造一次浪费资源
        Set<String> permissionNameSet = permissionMapper.getPermissionNameByUserId(userId);
        List<String> roleNameList = roleMapper.getRoleNameByUserId(userId);
        roleNameList.forEach(roleName -> permissionNameSet.add(SPRING_SECURITY_ROLE_PREFIX + roleName));
        return Result.success(permissionNameSet);
    }

    /**
     * 更新权限名，新权限名必须不存在
     *
     * @param id 权限编号
     * @param newPermissionName 新权限名
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updatePermissionName(Long id, String newPermissionName) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 去除权限名两边空白符
        newPermissionName = newPermissionName.trim();

        // 判断新权限名存不存在，新权限名必须不存在
        count = permissionMapper.countByPermissionName(newPermissionName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This permission name have been exists.");
        }

        // 更新权限名
        permissionMapper.updatePermissionName(id, newPermissionName);
        return getPermission(id);
    }

    /**
     * 更新授权路径
     *
     * @param id 权限编号
     * @param newAuthorizationUrl 新授权路径
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updateAuthorizationUrl(Long id, String newAuthorizationUrl) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 去除授权路径两边空白符
        // 更新授权路径
        permissionMapper.updateAuthorizationUrl(id, newAuthorizationUrl.trim());
        return getPermission(id);
    }

    /**
     * 更新权限描述
     *
     * @param id 权限编号
     * @param newDescription 新权限描述
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updateDescription(Long id, String newDescription) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 去除权限描述两边空白符
        // 更新权限描述
        permissionMapper.updateDescription(id, newDescription.trim());
        return getPermission(id);
    }

    /**
     * 禁用权限（且子权限可用状态也被禁用，递归禁用）
     *
     * @param id 权限编号
     * @return Result<Map<String, Object>> 禁用的数量和禁用后的权限对象，分别对应的key为totalDisableCount和newPermission
     */
    @Override
    public Result<Map<String, Object>> disablePermission(Long id) {
        // 判断该权限存不存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 判断该权限是否已经被禁用
        count = permissionMapper.countByIdAndAvailable(id, false);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This permission have bean disable.");
        }

        // 递归的禁用权限
        int totalDisableCount = recursiveDisablePermission(id);
        Map<String, Object> map = new HashMap<>();
        map.put("totalDisableCount", totalDisableCount);
        map.put("newPermission", getPermission(id));
        return Result.success(map);
    }

    /**
     * 解禁权限（且子权限可用状态也被解禁，递归解禁）
     *
     * @param id 权限编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的权限对象，分别对应的key为totalEnableCount和newPermission
     */
    @Override
    public Result<Map<String, Object>> enablePermission(Long id) {
        // 判断该权限存不存在
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 不能解禁已经有效的权限
        if (permissionDO.getAvailable()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This permission is available.");
        }

        // 如果该权限的父权限编号不为0
        // 判断该权限的父权限是否已经被禁用，如果父权限已经被禁用，则无法解禁该权限
        if (!permissionDO.getParentPermissionId().equals(0L)) {
            int count = permissionMapper.countByIdAndAvailable(permissionDO.getParentPermissionId(), false);
            if (count > 0) {
                return Result.fail(ErrorCode.INVALID_PARAMETER,
                        "Can't enable this permission, because the parent permission is disable.");
            }
        }

        // 递归的解禁权限
        int totalEnableCount = recursiveEnablePermission(id);
        Map<String, Object> map = new HashMap<>();
        map.put("totalEnableCount", totalEnableCount);
        map.put("newPermission", getPermission(id));
        return Result.success(map);
    }

    /**
     * 设置父权限
     * 设置parentPermissionId为0表示取消父权限设置
     * 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
     *
     * @param id 权限编号
     * @param parentPermissionId 父权限编号
     * @return Result<Map<String, Object>>
     *          禁用的数量和设置父权限后的权限对象，分别对应的key为totalDisableCount和newPermission
     *          这里的禁用是因为如果父权限为禁用，则该权限必须也递归的禁用
     */
    @Override
    public Result<Map<String, Object>> setParentPermission(Long id, Long parentPermissionId) {
        // 判断该权限存不存在
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "This permission not exists.");
        }

        // 如果原来的父权限编号和要设置的父权限编号相同，则直接返回
        if (permissionDO.getParentPermissionId().equals(parentPermissionId)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The parent permission has not changed.");
        }

        // 若父权限编号不为0，则判断要设置的父权限是否存在
        if (parentPermissionId != 0) {
            int count = permissionMapper.count(parentPermissionId);
            if (count < 1) {
                return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                        "This parent permission not exists.");
            }
        }

        // 设置父权限
        permissionMapper.updateParentPermissionId(id, parentPermissionId);

        // 如果要设置的父权限编号为0（取消父权限）
        // 或者要设置的父权限的状态为可用
        // 或者要设置的父权限的状态为禁用且当前权限的状态也为禁用，则直接返回
        if (parentPermissionId.equals(0L)
                || permissionMapper.countByIdAndAvailable(parentPermissionId, true) == 1
                || !permissionDO.getAvailable()) {
            Map<String, Object> map = new HashMap<>();
            map.put("totalDisableCount", 0);
            map.put("newPermission", getPermission(id));
            return Result.success(map);
        }

        // 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
        return disablePermission(id);
    }

    /**
     * 递归的禁用权限
     *
     * @param id 权限编号
     * @return 总共禁用的权限数量
     */
    private int recursiveDisablePermission(Long id) {
        int count = permissionMapper.updateAvailable(id, false);
        List<Long> permissionIdList = permissionMapper.getIdListByParentPermissionIdAndAvailable(id, true);
        for (Long permissionId : permissionIdList) {
            count += recursiveDisablePermission(permissionId);
        }
        return count;
    }

    /**
     * 递归的解禁权限
     *
     * @param id 权限编号
     * @return 总共解禁的权限数量
     */
    private int recursiveEnablePermission(Long id) {
        int count = permissionMapper.updateAvailableIfUnavailable(id);
        List<Long> permissionIdList = permissionMapper.getIdListByParentPermissionId(id);
        for (Long permissionId : permissionIdList) {
            count += recursiveEnablePermission(permissionId);
        }
        return count;
    }

}
