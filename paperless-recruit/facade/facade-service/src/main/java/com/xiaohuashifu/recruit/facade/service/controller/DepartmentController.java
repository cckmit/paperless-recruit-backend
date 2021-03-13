package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.authorize.DepartmentContext;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：部门控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "部门")
@RestController
@Validated
public class DepartmentController {

    private final DepartmentManager departmentManager;

    private final OrganizationContext organizationContext;

    public DepartmentController(DepartmentManager departmentManager, OrganizationContext organizationContext) {
        this.departmentManager = departmentManager;
        this.organizationContext = organizationContext;
    }

    @ApiOperation(value = "创建部门", notes = "Role: organization")
    @PostMapping("/departments")
    @PreAuthorize("hasRole('organization')")
    public DepartmentVO createDepartment(@RequestBody CreateDepartmentRequest request) {
        return departmentManager.createDepartment(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "删除部门", notes = "Role: organization")
    @DeleteMapping("/departments/{departmentId}")
    @Owner(id = "#departmentId", context = DepartmentContext.class)
    public void removeDepartment(@ApiParam("部门编号") @PathVariable Long departmentId) {
        departmentManager.removeDepartment(departmentId);
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/departments/{departmentId}")
    public DepartmentVO getDepartment(@ApiParam("部门编号") @PathVariable Long departmentId) {
        return departmentManager.getDepartment(departmentId);
    }

    @ApiOperation(value = "列出部门")
    @GetMapping("/departments")
    public List<DepartmentVO> listDepartments(DepartmentQuery departmentQuery) {
        return departmentManager.listDepartments(departmentQuery);
    }

    @ApiOperation(value = "更新部门", notes = "Role: organization")
    @PutMapping("/departments/{departmentId}")
    @PreAuthorize("hasRole('organization')")
    @Owner(id = "#departmentId", context = DepartmentContext.class)
    public DepartmentVO updateDepartment(@ApiParam("部门编号") @PathVariable Long departmentId,
                            @RequestBody @NotAllFieldsNull UpdateDepartmentRequest request) {
        return departmentManager.updateDepartment(departmentId, request);
    }

}
