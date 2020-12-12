package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        System.out.println(organizationService.createOrganization("827032783@qq.com",
                "485556", "123456"));
    }

    @Test
    public void addLabel() {
    }

    @Test
    public void removeLabel() {
    }

    @Test
    public void getOrganization() {
        System.out.println(organizationService.getOrganization(1L));
    }

    @Test
    public void listOrganizations() {
    }

    @Test
    public void updateOrganizationName() {
    }

    @Test
    public void updateAbbreviationOrganizationName() {
    }

    @Test
    public void updateIntroduction() {
    }

    @Test
    public void updateLogo() {
    }

    @Test
    public void increaseMemberNumber() {
    }

    @Test
    public void decreaseMemberNumber() {
    }

    @Test
    public void disableOrganization() {
    }

    @Test
    public void enableOrganization() {
    }

    @Test
    public void sendEmailAuthCodeForSignUp() {
        System.out.println(organizationService.sendEmailAuthCodeForSignUp("827032783@qq.com"));
    }

}