package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/8 19:36
 */
public class OrganizationLabelServiceImplTest {

    private OrganizationLabelService organizationLabelService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationLabelServiceTest");
        ReferenceConfig<OrganizationLabelService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService");
        reference.setApplication(application);
        reference.setInterface(OrganizationLabelService.class);
        reference.setTimeout(10000000);
        organizationLabelService = reference.get();
    }

    @Test
    public void saveOrganizationLabel() {
        System.out.println(organizationLabelService.createOrganizationLabel("3131232"));
    }

    @Test
    public void increaseReferenceNumberOrSaveOrganizationLabel() {
        System.out.println(organizationLabelService.increaseReferenceNumberOrSaveOrganizationLabel("创业"));
    }

    @Test
    public void listOrganizationLabels() {
        System.out.println(organizationLabelService.listOrganizationLabels(
                OrganizationLabelQuery
                        .builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .build()));
    }

    @Test
    public void disableOrganizationLabel() {
        System.out.println(organizationLabelService.disableOrganizationLabel(5L));
    }

    @Test
    public void enableOrganizationLabel() {
        System.out.println(organizationLabelService.enableOrganizationLabel(4L));
    }

}