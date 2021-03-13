package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationCoreMemberRequest;
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
    public void createOrganizationCoreMember() {
        System.out.println(organizationCoreMemberService.createOrganizationCoreMember(
                CreateOrganizationCoreMemberRequest.builder()
                        .organizationId(1L)
                        .avatarUrl("organizations/core-members/avartars/b54a4e2d50f24dd891b92c2519c6492a社科部科科.jpg")
                        .introduction("社科部部长")
                        .memberName("姜大白")
                        .position("社科部部长")
                        .priority(1).build()));
    }

    @Test
    public void removeOrganizationCoreMember() {
        organizationCoreMemberService.removeOrganizationCoreMember(1L);
    }

    @Test
    public void listOrganizationCoreMembersByOrganizationId() {
        System.out.println(organizationCoreMemberService.listOrganizationCoreMembersByOrganizationId(1L));
    }

    @Test
    public void updateOrganizationCoreMember() {
        System.out.println(organizationCoreMemberService.updateOrganizationCoreMember(
                UpdateOrganizationCoreMemberRequest.builder()
                        .id(6L)
                        .priority(3)
                        .build()
        ));
    }
}