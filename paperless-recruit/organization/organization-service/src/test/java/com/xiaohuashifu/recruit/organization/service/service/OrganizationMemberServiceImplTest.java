package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        System.out.println(organizationMemberService.sendInvitation(1L, "xiaohuashifu"));
    }

    @Test
    public void acceptInvitation() {
        System.out.println(organizationMemberService.acceptInvitation(7L));
    }

    @Test
    public void listOrganizationMemberDTO() {
    }

    @Test
    public void listOrganizationMemberInvitationDTO() {
    }

    @Test
    public void updateDepartment() {
        System.out.println(organizationMemberService.updateDepartment(1L, 2L));
    }

    @Test
    public void updateOrganizationPosition() {
        System.out.println(organizationMemberService.updateOrganizationPosition(
                1L, 1L));
    }

    @Test
    public void updateMemberStatus() {
        System.out.println(organizationMemberService.updateMemberStatus(
                1L, OrganizationMemberStatusEnum.ON_JOB));
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