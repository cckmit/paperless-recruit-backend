package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.service.UserServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 21:10
 */
public class RoleServiceImplTest extends UserServiceApplicationTests {

    @Reference
    private RoleService roleService;

    @Test
    public void getRoleListByUserId() {
        System.out.println(roleService.getRoleListByUserId(1L));
    }

    @Test
    public void saveRole() {
        final RoleDTO roleDTO = new RoleDTO.Builder()
                .parentRoleId(0L)
                .roleName("root")
                .description("超级用户")
                .available(true).build();
        System.out.println(roleService.saveRole(roleDTO));
    }

    @Test
    public void getRole() {
    }
}