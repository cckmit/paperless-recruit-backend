package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/12 20:54
 */
public class OrganizationServiceImplTest {

    private OrganizationService organizationService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationServiceTest");
        ReferenceConfig<OrganizationService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationService");
        reference.setApplication(application);
        reference.setInterface(OrganizationService.class);
        reference.setTimeout(10000000);
        organizationService = reference.get();
    }

    @Test
    public void createOrganization() {
        System.out.println(organizationService.createOrganization(
                CreateOrganizationRequest.builder().email("827032783@qq.com").authCode( "453669").password("123456").build()));
    }

    @Test
    public void getOrganization() {
        System.out.println(organizationService.getOrganization(1L));
    }

    @Test
    public void listOrganizations() {
        System.out.println(organizationService.listOrganizations(
                OrganizationQuery.builder().pageNum(1L).pageSize(50L).build()));
    }

    @Test
    public void sendEmailAuthCodeForSignUp() {
        organizationService.sendEmailAuthCodeForCreateOrganization("827032783@qq.com");
    }

    @Test
    public void updateOrganization() {
        System.out.println(
                organizationService.updateOrganization(
                        UpdateOrganizationRequest.builder().id(1L)
                                .organizationType("综合")
                                .size("200-300人")
                                .address("创客空间")
                                .website("www.xiaohuashifu.top").build()));
    }
}