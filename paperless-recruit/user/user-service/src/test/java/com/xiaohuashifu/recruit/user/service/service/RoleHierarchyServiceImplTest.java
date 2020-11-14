package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.service.RoleHierarchyService;
import com.xiaohuashifu.recruit.user.service.UserServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/15 00:06
 */
public class RoleHierarchyServiceImplTest extends UserServiceApplicationTests {

    @Reference
    private RoleHierarchyService roleHierarchyService;

    @Test
    public void createRoleHierarchy() {
        System.out.println(roleHierarchyService.createRoleHierarchy());
    }
}