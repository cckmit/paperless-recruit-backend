package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/14 22:06
 */
public class DepartmentLabelServiceImplTest {

    private DepartmentLabelService departmentLabelService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("departmentLabelServiceTest");
        ReferenceConfig<DepartmentLabelService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20885/com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService");
        reference.setApplication(application);
        reference.setInterface(DepartmentLabelService.class);
        reference.setTimeout(10000000);
        departmentLabelService = reference.get();
    }

    @Test
    public void saveDepartmentLabel() {
        System.out.println(departmentLabelService.saveDepartmentLabel("软件"));
    }

    @Test
    public void increaseReferenceNumberOrSaveDepartmentLabel() {
        System.out.println(departmentLabelService.increaseReferenceNumberOrSaveDepartmentLabel("创新"));
    }

    @Test
    public void listDepartmentLabels() {
        System.out.println(departmentLabelService.listDepartmentLabels(DepartmentLabelQuery.builder()
                .pageNum(1L)
                .pageSize(50L)
                .orderByReferenceNumberDesc(true)
                .build()));
    }

    @Test
    public void disableDepartmentLabel() {
        System.out.println(departmentLabelService.disableDepartmentLabel(5L));
    }

    @Test
    public void enableDepartmentLabel() {
        System.out.println(departmentLabelService.enableDepartmentLabel(5L));
    }

    @Test
    public void isValidDepartmentLabel() {
        System.out.println(departmentLabelService.isValidDepartmentLabel("创新"));
    }
}