package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 描述：授权（权限）服务，用于服务认证授权框架
 *
 * @author: xhsf
 * @create: 2020/11/14 20:24
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final PermissionMapper permissionMapper;

    private final RoleMapper roleMapper;

    /**
     * 角色大于号
     */
    private static final String ROLE_GREATER_THAN = " > ";

    /**
     * 当权限没有父亲时的 parentPermissionId
     */
    private static final Long NO_PARENT_PERMISSION_ID = 0L;

    /**
     * 当角色没有父亲时的 parentRoleId
     */
    private static final Long NO_PARENT_ROLE_ID = 0L;

    public AuthorityServiceImpl(PermissionMapper permissionMapper, RoleMapper roleMapper) {
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public String createRoleHierarchy(String roleNamePrefix) {
        // 关于权限的 RoleHierarchy
        String permissionRoleHierarchy = createPermissionRoleHierarchy();
        // 关于角色的 RoleHierarchy
        String roleRoleHierarchy = createRoleRoleHierarchy(roleNamePrefix);
        // RoleHierarchy
        return permissionRoleHierarchy + roleRoleHierarchy;
    }

    @Override
    public Set<String> listAuthoritiesByUserId(Long userId, String roleNamePrefix) {
        // 下面将直接复用这个 Set，不再构造一次浪费资源
        Set<String> permissionNames = permissionMapper.selectAvailablePermissionNamesByUserId(userId);
        Set<String> roleNames = roleMapper.selectAvailableRoleNamesByUserId(userId);
        roleNames.forEach(roleName -> permissionNames.add(roleNamePrefix + roleName));
        return permissionNames;
    }

    @Override
    public Map<String, String> createPermissionNameAuthorizationUrlMap(String authorityPrefix) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getAvailable, true);
        List<PermissionDO> permissionDOList = permissionMapper.selectList(wrapper);
        Map<String, String> permissionNameAuthorizationUrlMap = new ConcurrentHashMap<>();
        for (PermissionDO permissionDO : permissionDOList) {
            permissionNameAuthorizationUrlMap.put(authorityPrefix + permissionDO.getPermissionName(),
                    permissionDO.getAuthorizationUrl());
        }
        return permissionNameAuthorizationUrlMap;
    }

    /**
     * 创建 Permission 的 RoleHierarchy
     *
     * @return PermissionRoleHierarchy
     */
    private String createPermissionRoleHierarchy() {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getAvailable, true);
        List<PermissionDO> permissionDOList = permissionMapper.selectList(wrapper);
        Map<Long, String> permissionDOMap = permissionDOList.stream()
                .collect(Collectors.toMap(PermissionDO::getId, PermissionDO::getPermissionName));
        StringBuilder roleHierarchy = new StringBuilder();
        for (PermissionDO permissionDO : permissionDOList) {
            if (Objects.equals(permissionDO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
                continue;
            }
            String parentPermissionName = permissionDOMap.get(permissionDO.getParentPermissionId());
            String permissionName = permissionDO.getPermissionName();
            roleHierarchy.append(parentPermissionName).append(ROLE_GREATER_THAN).append(permissionName).append('\n');
        }
        return roleHierarchy.toString();
    }

    /**
     * 创建 Role 的 RoleHierarchy
     *
     * @param roleNamePrefix 角色名前缀
     * @return RoleRoleHierarchy
     */
    private String createRoleRoleHierarchy(String roleNamePrefix) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getAvailable, true);
        List<RoleDO> roleDOList = roleMapper.selectList(wrapper);
        Map<Long, String> roleDOMap = roleDOList.stream()
                .collect(Collectors.toMap(RoleDO::getId, RoleDO::getRoleName));
        StringBuilder roleHierarchy = new StringBuilder();
        for (RoleDO roleDO : roleDOList) {
            if (Objects.equals(roleDO.getParentRoleId(), NO_PARENT_ROLE_ID)) {
                continue;
            }
            String parentRoleName = roleNamePrefix + roleDOMap.get(roleDO.getParentRoleId());
            String roleName = roleNamePrefix + roleDO.getRoleName();
            roleHierarchy.append(parentRoleName).append(ROLE_GREATER_THAN).append(roleName).append('\n');
        }
        return roleHierarchy.toString();
    }

}
