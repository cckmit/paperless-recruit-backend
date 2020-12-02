package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.result.Result;

/**
 * 描述：获取 RoleHierarchy 的 setHierarchy 方法的参数的字符串的服务
 *      用于构建带层级的关系
 *
 * @author: xhsf
 * @create: 2020/11/14 20:24
 */
public interface RoleHierarchyService {
    /**
     * 这个服务会返回当前权限的层级结构，用于构建 RoleHierarchy
     * 例如：    "root > user\n" +
     *           "user > get_user\n" +
     *           "user > update_user\n" +
     *           "user > create_user"
     * @return 权限的层级结构字符串
     */
    Result<String> createRoleHierarchy();
}
