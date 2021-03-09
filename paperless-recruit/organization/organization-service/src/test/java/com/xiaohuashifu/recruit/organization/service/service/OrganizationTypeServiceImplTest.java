package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationTypeService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/3/9 14:21
 */
public class OrganizationTypeServiceImplTest {

    private OrganizationTypeService organizationTypeService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationTypeServiceTest");
        ReferenceConfig<OrganizationTypeService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationTypeService");
        reference.setApplication(application);
        reference.setInterface(OrganizationTypeService.class);
        reference.setTimeout(10000000);
        organizationTypeService = reference.get();
    }

    @Test
    public void getOrganizationTypeByTypeName() {
        System.out.println(organizationTypeService.getOrganizationTypeByTypeName("综合"));
    }

    @Test
    public void listOrganizationTypes() {
        System.out.println(organizationTypeService.listOrganizationTypes(
                OrganizationTypeQuery.builder()
                        .pageNum(1L)
                        .pageSize(10L)
                        .build()));
    }
}