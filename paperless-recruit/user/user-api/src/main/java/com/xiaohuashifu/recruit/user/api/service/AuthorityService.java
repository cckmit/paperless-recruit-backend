package com.xiaohuashifu.recruit.user.api.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;
import java.util.Set;

/**
 * 描述：授权（权限）服务，用于服务认证授权框架
 *
 * @author: xhsf
 * @create: 2020/11/14 20:24
 */
public interface AuthorityService {

    /**
     * 这个服务会返回当前权限的层级结构，用于构建 RoleHierarchy
     * 会剔除不可用的权限（Permission）和角色（Role）
     *
     * @private 内部方法
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
    String createRoleHierarchy(@NotNull String roleNamePrefix);

    /**
     * 创建 PermissionNameAuthorizationUrlMap，用于基于路径（URL）的鉴权
     * 这里只会获取可用的 Permission
     *
     * @param authorityPrefix 权限的前缀
     * @return PermissionNameAuthorizationUrlMap
     */
    Map<String, String> createPermissionNameAuthorizationUrlMap(@NotNull String authorityPrefix);

    /**
     * 通过用户 id 获取用户权限 Authority 列表
     * 该权限代表的是权限字符串，而不是 Permission 对象
     * 主要用于 Spring Security 框架鉴权使用
     * 包含角色和权限
     * 角色的转换格式为：roleNamePrefix_{role_name}
     * 权限的转换格式为：{permission_name}
     * 这里只会返回可用的 Permission 和 Role
     *
     * @param userId 用户id
     * @param roleNamePrefix 角色名前缀
     * @return 用户的权限 Authority 列表，可能返回空列表
     */
    Set<String> listAuthoritiesByUserId(@NotNull @Positive Long userId, @NotNull String roleNamePrefix);

}
