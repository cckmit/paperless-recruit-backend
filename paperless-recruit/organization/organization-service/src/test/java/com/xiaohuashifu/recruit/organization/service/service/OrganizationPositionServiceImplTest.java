package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationPositionService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/15 15:14
 */
public class OrganizationPositionServiceImplTest {

    private OrganizationPositionService organizationPositionService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationPositionServiceTest");
        ReferenceConfig<OrganizationPositionService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationPositionService");
        reference.setApplication(application);
        reference.setInterface(OrganizationPositionService.class);
        reference.setTimeout(10000000);
        organizationPositionService = reference.get();
    }

    @Test
    public void saveOrganizationPosition() {
        System.out.println(organizationPositionService.createOrganizationPosition(
                1L, "中心主席", 2));
    }

    @Test
    public void removeOrganizationPosition() {
        System.out.println(organizationPositionService.removeOrganizationPosition(2L));
    }

    @Test
    public void getOrganizationPosition() {
        System.out.println(organizationPositionService.getOrganizationPosition(2L));
    }

    @Test
    public void listOrganizationPositions() {
        System.out.println(organizationPositionService.listOrganizationPositions
                (OrganizationPositionQuery.builder().pageNum(1L).pageSize(50L).organizationId(1L).build()));
    }

    @Test
    public void updatePositionName() {
        System.out.println(organizationPositionService.updatePositionName(1L, "科联主席"));
    }

    @Test
    public void updatePriority() {
        System.out.println(organizationPositionService.updatePriority(1L, 0));
    }
}