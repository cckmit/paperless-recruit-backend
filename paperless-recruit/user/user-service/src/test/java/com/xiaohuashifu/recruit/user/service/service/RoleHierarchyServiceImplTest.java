package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.service.RoleHierarchyService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/15 00:06
 */
public class RoleHierarchyServiceImplTest {

    private RoleHierarchyService roleHierarchyService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("userServiceTest");
        ReferenceConfig<RoleHierarchyService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.RoleHierarchyService");
        reference.setApplication(application);
        reference.setInterface(RoleHierarchyService.class);
        roleHierarchyService = reference.get();
    }

    @Test
    public void createRoleHierarchy() {
        System.out.println(roleHierarchyService.createRoleHierarchy());
    }
}