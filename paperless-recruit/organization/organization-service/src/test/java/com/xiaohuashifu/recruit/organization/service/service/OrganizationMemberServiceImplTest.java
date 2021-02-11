package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationMemberRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/16 20:00
 */
public class OrganizationMemberServiceImplTest {

    private OrganizationMemberService organizationMemberService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("organizationMemberServiceTest");
        ReferenceConfig<OrganizationMemberService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService");
        reference.setApplication(application);
        reference.setInterface(OrganizationMemberService.class);
        reference.setTimeout(10000000);
        organizationMemberService = reference.get();
    }

    @Test
    public void sendInvitation() {
//        System.out.println(organizationMemberService.sendInvitation(1L,
//                "scau_recruit_0983189606"));
    }

    @Test
    public void acceptInvitation() {
        System.out.println(organizationMemberService.acceptInvitation(8L));
    }

    @Test
    public void listOrganizationMemberDTO() {
        System.out.println(organizationMemberService.listOrganizationMember(
                OrganizationMemberQuery.builder().pageNum(1L).pageSize(50L).organizationId(1L).build()));
    }

    @Test
    public void listOrganizationMemberInvitationDTO() {
        System.out.println(organizationMemberService.listOrganizationMemberInvitation(
                OrganizationMemberInvitationQuery.builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .organizationId(1L)
                        .build()
        ));
    }

    @Test
    public void updateOrganizationMember() {
        System.out.println(organizationMemberService.updateOrganizationMember(
                UpdateOrganizationMemberRequest.builder().id(1L).organizationPositionId(1L).build()));
    }

    @Test
    public void rejectInvitation() {
        System.out.println(organizationMemberService.rejectInvitation(4L));
    }

    @Test
    public void updateInvitationStatusToExpired() {
        System.out.println(organizationMemberService.updateInvitationStatusToExpired(7L));
    }
}