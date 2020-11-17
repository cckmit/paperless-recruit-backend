package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
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
        roleService = reference.get();
    }

    @Test
    public void getRoleListByUserId() {
        System.out.println(roleService.getRoleListByUserId(1L));
    }

    @Test
    public void saveRole() {
        final RoleDTO roleDTO = new RoleDTO.Builder()
                .parentRoleId(8L)
                .roleName("   test5   ")
                .description("  测试用户5  \n")
                .available(true).build();
        System.out.println(roleService.saveRole(roleDTO));
    }

    @Test
    public void getRole() {
        System.out.println(roleService.getRole(new RoleQuery()));
    }

    @Test
    public void deleteRole() {
        System.out.println(roleService.deleteRole(7L));
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
        System.out.println(roleService.disableRole(6L));
    }

    @Test
    public void enableRole() {
        System.out.println(roleService.enableRole(5L));
    }

    @Test
    public void setParentRole() {
        System.out.println(roleService.setParentRole(8L, 9L));
    }
}