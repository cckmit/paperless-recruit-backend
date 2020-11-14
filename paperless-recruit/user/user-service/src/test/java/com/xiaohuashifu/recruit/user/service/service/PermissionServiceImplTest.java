package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.service.UserServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 21:20
 */
public class PermissionServiceImplTest extends UserServiceApplicationTests {

    @Reference
    private PermissionService permissionService;

    @Reference
    private RoleService roleService;

    @Test
    public void getPermissionListByRoleIdList() {
        final Result<List<RoleDTO>> getRoleResult = roleService.getRoleListByUserId(1L);
        final List<Long> roleIdList = getRoleResult.getData().stream()
                .map(RoleDTO::getId)
                .collect(Collectors.toList());

        System.out.println(permissionService.getPermissionByRoleIdList(roleIdList));
    }

    @Test
    public void getPermissionByUserId() {
        System.out.println(permissionService.getPermissionByUserId(1L));
    }

    @Test
    public void getPermission() {
        System.out.println(permissionService.getPermission(new PermissionQuery.Builder().pageSize(10000L).build()));
    }
}