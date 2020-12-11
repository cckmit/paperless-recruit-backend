package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
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

    /**
     * 这个服务会返回当前权限的层级结构，用于构建 RoleHierarchy
     * 会剔除不可用的权限（Permission）和角色（Role）
     *
     * @errorCode InvalidParameter: roleNamePrefix 格式错误
     *
     * 例如：    "root > user\n" +
     *           "user > get_user\n" +
     *           "user > update_user\n" +
     *           "user > create_user\n" +
     *           "ROLE_root > ROLE_user\n" +
     *           "ROLE_root > ROLE_interviewee\n" +
     *           "ROLE_root > ROLE_interviewer"
     * @param roleNamePrefix 角色名前缀
     * @return 权限的层级结构字符串
     */
    @Override
    public Result<String> createRoleHierarchy(String roleNamePrefix) {
        // 关于权限的 RoleHierarchy
        String permissionRoleHierarchy = createPermissionRoleHierarchy();
        // 关于角色的 RoleHierarchy
        String roleRoleHierarchy = createRoleRoleHierarchy(roleNamePrefix);
        // RoleHierarchy
        String roleHierarchy = permissionRoleHierarchy + roleRoleHierarchy;
        return Result.success(roleHierarchy);
    }

    /**
     * 通过用户 id 获取用户权限 Authority 列表
     * 该权限代表的是权限字符串，而不是 Permission 对象
     * 主要用于 Spring Security 框架鉴权使用
     * 包含角色和权限
     * 角色的转换格式为：ROLE_{role_name}
     * 权限的转换格式为：{permission_name}
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param userId 用户id
     * @param roleNamePrefix 角色名前缀
     * @return 用户的权限 Authority 列表，可能返回空列表
     */
    @Override
    public Result<Set<String>> listAuthoritiesByUserId(Long userId, String roleNamePrefix) {
        // 下面将直接复用这个 Set，不再构造一次浪费资源
        Set<String> permissionNameSet = permissionMapper.listPermissionNamesByUserId(userId);
        List<String> roleNameList = roleMapper.listRoleNamesByUserId(userId);
        roleNameList.forEach(roleName -> permissionNameSet.add(roleNamePrefix + roleName));
        return Result.success(permissionNameSet);
    }

    /**
     * 创建 PermissionNameAuthorizationUrlMap，用于基于路径（URL）的鉴权
     *
     * @return PermissionNameAuthorizationUrlMap
     */
    @Override
    public Result<Map<String, String>> createPermissionNameAuthorizationUrlMap(String authorityPrefix) {
        List<PermissionDO> permissionDOList = permissionMapper.listAllAvailablePermissions();
        Map<String, String> permissionNameAuthorizationUrlMap = new ConcurrentHashMap<>();
        for (PermissionDO permissionDO : permissionDOList) {
            permissionNameAuthorizationUrlMap.put(authorityPrefix + permissionDO.getPermissionName(),
                    permissionDO.getAuthorizationUrl());
        }
        return Result.success(permissionNameAuthorizationUrlMap);
    }

    /**
     * 创建 Permission 的 RoleHierarchy
     *
     * @return PermissionRoleHierarchy
     */
    private String createPermissionRoleHierarchy() {
        List<PermissionDO> permissionDOList = permissionMapper.listAllAvailablePermissions();
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
        List<RoleDO> roleDOList = roleMapper.listAllAvailableRoles();
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
