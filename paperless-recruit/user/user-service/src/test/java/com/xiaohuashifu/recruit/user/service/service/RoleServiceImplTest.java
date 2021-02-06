package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.request.CreateRoleRequest;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 21:10
 */
public class RoleServiceImplTest {

    private RoleService roleService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("roleServiceTest");
        ReferenceConfig<RoleService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.RoleService");
        reference.setApplication(application);
        reference.setInterface(RoleService.class);
        reference.setTimeout(100000000);
        roleService = reference.get();
    }

    @Test
    public void createRole() {
        CreateRoleRequest request = CreateRoleRequest.builder()
                .parentRoleId(15L).roleName("test11").description("测试").available(true).build();
        System.out.println(roleService.createRole(request));
    }

    @Test
    public void createUserRole() {
        System.out.println(roleService.createUserRole(12L, 18L));
    }

    @Test
    public void createRolePermission() {
        System.out.println(roleService.createRolePermission(18L, 20L));
    }

    @Test
    public void listRoles() {
        System.out.println(roleService.listRoles(RoleQuery.builder().pageNum(1L).pageSize(10L).build()));
    }

    @Test
    public void deleteRole() {
        roleService.removeRole(18L);
    }

    @Test
    public void deleteUserRole() {
        roleService.removeUserRole(7L, 10L);
    }

    @Test
    public void deleteRolePermission() {
        roleService.removeRolePermission(11L, 9L);
    }

    @Test
    public void updateRoleName() {
        System.out.println(roleService.updateRoleName(8L, " test2 "));
    }

    @Test
    public void updateDescription() {
        System.out.println(roleService.updateDescription(8L, " 测试角色2 "));
    }

    @Test
    public void disableRole() {
        System.out.println(roleService.disableRole(15L));
    }

    @Test
    public void enableRole() {
        System.out.println(roleService.enableRole(15L));
    }

    @Test
    public void setParentRole() {
        System.out.println(roleService.setParentRole(1L, 5L));
    }

}