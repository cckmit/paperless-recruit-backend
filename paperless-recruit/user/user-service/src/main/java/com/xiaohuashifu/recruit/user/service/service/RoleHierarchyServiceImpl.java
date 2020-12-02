package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.RoleHierarchyService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * 角色大于号
     */
    private static final String ROLE_GREATER_THAN = " > ";

    /**
     * 当权限没有父亲时的 parentPermissionId
     */
    private static final Long NO_PARENT_PERMISSION_ID = 0L;

    /**
     * 这个服务会返回当前权限的层级结构，用于构建RoleHierarchy
     * 例如：    "root > user\n" +
     *           "user > get_user\n" +
     *           "user > update_user\n" +
     *           "user > create_user"
     * @return 权限的层级结构字符串
     */
    @Override
    public Result<String> createRoleHierarchy() {
        Result<List<PermissionDTO>> getAllPermissionResult = permissionService.listAllPermissions();
        List<PermissionDTO> permissionDTOList = getAllPermissionResult.getData();
        Map<Long, String> permissionDTOMap = permissionDTOList.stream()
                .collect(Collectors.toMap(PermissionDTO::getId, PermissionDTO::getPermissionName));
        StringBuilder roleHierarchy = new StringBuilder();
        for (PermissionDTO permissionDTO : permissionDTOList) {
            if (Objects.equals(permissionDTO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
                continue;
            }
            String parentPermissionName = permissionDTOMap.get(permissionDTO.getParentPermissionId());
            String permissionName = permissionDTO.getPermissionName();
            roleHierarchy.append(parentPermissionName).append(ROLE_GREATER_THAN).append(permissionName).append('\n');
        }
        return Result.success(roleHierarchy.toString());
    }
}
