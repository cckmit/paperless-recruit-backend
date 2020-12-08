package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        System.out.println(organizationLabelService.saveOrganizationLabel("3131232"));
    }

    @Test
    public void increaseReferenceNumberOrSaveOrganizationLabel() {
        System.out.println(organizationLabelService.increaseReferenceNumberOrSaveOrganizationLabel("创业"));
    }

    @Test
    public void listOrganizationLabels() {
        System.out.println(organizationLabelService.listOrganizationLabels(
                new OrganizationLabelQuery
                        .Builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .orderByReferenceNumberDesc(true)
                        .orderByUpdateTime(true)
                        .build()));
    }

    @Test
    public void disableOrganizationLabel() {
    }

    @Test
    public void enableOrganizationLabel() {
    }
}