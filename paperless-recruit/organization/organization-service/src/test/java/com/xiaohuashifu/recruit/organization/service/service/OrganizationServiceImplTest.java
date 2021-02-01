package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
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
        System.out.println(organizationService.createOrganization("827032783@qq.com",
                "453669", "123456"));
    }

    @Test
    public void addLabel() {
        System.out.println(organizationService.addLabel(1L, "软件"));
    }

    @Test
    public void removeLabel() {
        System.out.println(organizationService.removeLabel(1L, "创业"));
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
    public void updateOrganizationName() {
        System.out.println(organizationService.updateOrganizationName(1L, "华南农业大学学生科技创新与创业联合会"));
    }

    @Test
    public void updateAbbreviationOrganizationName() {
        System.out.println(organizationService.updateAbbreviationOrganizationName(1L, "校科联"));
    }

    @Test
    public void updateIntroduction() {
        System.out.println(organizationService.updateIntroduction(1L, "华南农业大学学生科技创新与创业联合会（以下简称校科联）是在学校团委指导下的面向华南农业大学全日制本科生和研究生的学术型组织。\n" +
                "本会以弘扬丁颖科学精神，积极发展学生科技创新与创业能力为宗旨，秉承创新、专注、严谨、奉献、联合的精神，立足于学生学术发展和学校的科研建设。通过组织和指导学科竞赛和科研实践，推动华南农业大学科技创新建设，为构建学术型综合性大学，培养全方位、高水平的科技人才做出贡献。"));
    }

    @Test
    public void updateLogo() throws IOException {
        System.out.println(organizationService.updateLogo(1L,
                "organizations/logos/738d8e7485d3455da22765e199cc65721601000702(1).jpg"));
    }

    @Test
    public void increaseMemberNumber() {
        System.out.println(organizationService.increaseNumberOfMembers(1L));
    }

    @Test
    public void decreaseMemberNumber() {
        System.out.println(organizationService.decreaseNumberOfMembers(1L));
    }

    @Test
    public void increaseNumberOfDepartments() {
        System.out.println(organizationService.increaseNumberOfDepartments(1L));
    }

    @Test
    public void decreaseNumberOfDepartments() {
        System.out.println(organizationService.decreaseNumberOfDepartments(1L));
    }

    @Test
    public void disableOrganization() {
        System.out.println(organizationService.disableOrganization(1L));
    }

    @Test
    public void enableOrganization() {
        System.out.println(organizationService.enableOrganization(1L));
    }

    @Test
    public void sendEmailAuthCodeForSignUp() {
        System.out.println(organizationService.sendEmailAuthCodeForSignUp("827032783@qq.com"));
    }

    @Test
    public void checkOrganizationStatus() {
        System.out.println(organizationService.checkOrganizationStatus(null));
    }

}