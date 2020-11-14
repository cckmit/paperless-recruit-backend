package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.RoleHierarchyService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述：获取RoleHierarchy的setHierarchy方法的参数的字符串的服务
 *      用于构建带层级的关系
 * 示例：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/14 20:28
 */
@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    @Reference
    private PermissionService permissionService;

    /**
     * 根级权限名
     */
    private static final String ROOT_PERMISSION_NAME = "root";

    /**
     * 这个服务会返回当前权限的层级结构，用于构建RoleHierarchy
     * 例如：    "all > user\n" +
     *           "user > get_user\n" +
     *           "user > update_user\n" +
     *           "user > create_user"
     * @return 权限的层级结构字符串
     */
    @Override
    public String createRoleHierarchy() {
        final Result<List<PermissionDTO>> getPermissionResult = permissionService.getPermission(
                new PermissionQuery.Builder().pageSize(Long.MAX_VALUE).build());
        final List<PermissionDTO> permissionDTOList = getPermissionResult.getData();
        final Map<Long, String> permissionDTOMap = permissionDTOList.stream()
                .collect(Collectors.toMap(PermissionDTO::getId, PermissionDTO::getPermissionName));
        StringBuilder roleHierarchy = new StringBuilder();
        for (PermissionDTO permissionDTO : permissionDTOList) {
            if (permissionDTO.getPermissionName().equals(ROOT_PERMISSION_NAME)) {
                continue;
            }
            String parentPermissionName = permissionDTOMap.get(permissionDTO.getParentPermissionId());
            String permissionName = permissionDTO.getPermissionName();
            roleHierarchy.append(parentPermissionName).append(" > ").append(permissionName).append('\n');
        }
        return roleHierarchy.toString();
    }
}
