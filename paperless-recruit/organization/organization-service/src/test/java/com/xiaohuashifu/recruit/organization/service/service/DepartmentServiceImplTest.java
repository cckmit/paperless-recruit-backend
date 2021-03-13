package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ValidationException;

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
        try {
            System.out.println(departmentService.createDepartment(CreateDepartmentRequest.builder()
                    .organizationId(null).departmentName("办公室").build()));
        } catch (ValidationException e) {
            e.printStackTrace();
            System.out.println(e.getClass());
        }
    }

    @Test
    public void getDepartment() {
        System.out.println(departmentService.getDepartment(1L));
    }

    @Test
    public void listDepartments() {
        System.out.println(departmentService.listDepartments(DepartmentQuery.builder()
                .pageNum(1L)
                .pageSize(50L)
                .build()));
    }

    @Test
    public void updateDepartment() {
//        System.out.println(departmentService.updateDepartment(1L, "自然科学部"));
    }

}