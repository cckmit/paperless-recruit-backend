package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/20 17:41
 */
public class OrganizationCoreMemberServiceImplTest {

    private OrganizationCoreMemberService organizationCoreMemberService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationCoreMemberServiceTest");
        ReferenceConfig<OrganizationCoreMemberService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService");
        reference.setApplication(application);
        reference.setInterface(OrganizationCoreMemberService.class);
        reference.setTimeout(10000000);
        organizationCoreMemberService = reference.get();
    }

    @Test
    public void saveOrganizationCoreMember() {
        System.out.println(organizationCoreMemberService.createOrganizationCoreMember(
                1L, 2L));
    }


    @Test
    public void deleteOrganizationCoreMember() {
        System.out.println(organizationCoreMemberService.removeOrganizationCoreMember(2L));
    }

    @Test
    public void listOrganizationCoreMembersByOrganizationId() {
        System.out.println(organizationCoreMemberService.listOrganizationCoreMembersByOrganizationId(1L));
    }
}