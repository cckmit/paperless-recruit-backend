package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.request.CreatePermissionRequest;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
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
 * @create: 2020/11/12 21:20
 */
public class PermissionServiceImplTest {

    private PermissionService permissionService;

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

        ApplicationConfig application1 = new ApplicationConfig();
        application1.setName("permissionServiceTest");
        ReferenceConfig<PermissionService> reference1 = new ReferenceConfig<>();
        reference1.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.PermissionService");
        reference1.setApplication(application1);
        reference1.setInterface(PermissionService.class);
        reference1.setTimeout(100000000);
        permissionService = reference1.get();
    }

    @Test
    public void createPermission() {
        System.out.println(permissionService.createPermission(CreatePermissionRequest.builder()
                .parentPermissionId(17L).permissionName("  test8  ").authorizationUrl("  /tests/test5  ")
                .description("  测试  ").available(true).build()));
    }

    @Test
    public void deletePermission() {
        permissionService.removePermission(14L);
    }

    @Test
    public void getPermission() {
        System.out.println(permissionService.getPermission(1L));
    }

    @Test
    public void testGetPermission() {
        System.out.println(permissionService.listPermissions(
                PermissionQuery.builder()
                        .permissionName("test")
                        .build()));
    }

    @Test
    public void updatePermission() {
//        System.out.println(permissionService.updatePermissionName(13L, "   test3  "));
    }

    @Test
    public void disablePermission() {
        System.out.println(permissionService.disablePermission(11L));
    }

    @Test
    public void enablePermission() {
        System.out.println(permissionService.enablePermission(13L));
    }

    @Test
    public void setParentPermission() {
        System.out.println(permissionService.setParentPermission(13L, 11L));
    }

}