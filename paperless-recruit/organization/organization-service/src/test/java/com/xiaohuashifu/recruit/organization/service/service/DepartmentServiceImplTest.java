package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.po.UpdateDepartmentLogoPO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/14 22:05
 */
public class DepartmentServiceImplTest {

    private DepartmentService departmentService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("departmentServiceTest");
        ReferenceConfig<DepartmentService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.DepartmentService");
        reference.setApplication(application);
        reference.setInterface(DepartmentService.class);
        reference.setTimeout(10000000);
        departmentService = reference.get();
    }

    @Test
    public void createDepartment() {
        System.out.println(departmentService.createDepartment(
                1L, "自然科学部1", "自科部"));
    }

    @Test
    public void addLabel() {
        System.out.println(departmentService.addLabel(1L, "项目"));
    }

    @Test
    public void removeLabel() {
        System.out.println(departmentService.removeLabel(1L, "项目"));
    }

    @Test
    public void getDepartment() {
        System.out.println(departmentService.getDepartment(1L));
    }

    @Test
    public void listDepartments() {
        System.out.println(departmentService.listDepartments(new DepartmentQuery.Builder()
                .pageNum(1L)
                .pageSize(50L)
                .departmentName("自然")
                .build()));
    }

    @Test
    public void updateDepartmentName() {
        System.out.println(departmentService.updateDepartmentName(1L, "自然科学部"));
    }

    @Test
    public void updateAbbreviationDepartmentName() {
        System.out.println(departmentService.updateAbbreviationDepartmentName(1L, "自科部"));
    }

    @Test
    public void updateIntroduction() {
        System.out.println(departmentService.updateIntroduction(1L,
                "1、参加科研项目和科技比赛，加强对成员的技术培训，培养和提高自身的科研力量和技术支持，拥有自身的科研成果；\n" +
                "2、加强内外交流，组织学生参观学校各学院或其他高校的实验室或工作室、开展项目分享会、科研交流会、科技成果展览会等，培养和促进学生的科研兴趣，并提供其学习、实践的机会；"));
    }

    @Test
    public void updateLogo() throws IOException {
        File file = new File("C:\\Users\\82703\\Desktop\\381056168614065362.png");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        System.out.println(departmentService.updateLogo(new UpdateDepartmentLogoPO.Builder()
                .id(1L).logo(bytes).logoExtensionName(".png").build()));
    }

    @Test
    public void increaseMemberNumber() {
        System.out.println(departmentService.increaseMemberNumber(1L));
    }

    @Test
    public void decreaseMemberNumber() {
        System.out.println(departmentService.decreaseMemberNumber(1L));
    }
}