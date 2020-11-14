package com.xiaohuashifu.recruit.authentication.api.service;

/**
 * 描述：获取RoleHierarchy的setHierarchy方法的参数的字符串的服务
 *      用于构建带层级的关系
 * 示例：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/14 20:24
 */
public interface RoleHierarchyService {
    /**
     * 这个服务会返回当前权限的层级结构，用于构建RoleHierarchy
     * 例如：    "all > user\n" +
     *           "user > get_user\n" +
     *           "user > update_user\n" +
     *           "user > create_user"
     * @return 权限的层级结构字符串
     */
    String createRoleHierarchy();
}
