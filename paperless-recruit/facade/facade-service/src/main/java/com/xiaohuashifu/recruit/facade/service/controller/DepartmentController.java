package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.DepartmentContext;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.request.BaseQueryRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentLabelPostRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentPatchRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('organization')")
    public DepartmentVO createDepartment(@RequestBody DepartmentPostRequest request) {
        return departmentManager.createDepartment(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "添加部门标签", notes = "Role: organization")
    @PostMapping("/departments/{departmentId}/labels")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('organization')")
    @Owner(id = "#departmentId", context = DepartmentContext.class)
    public DepartmentVO addLabel(@ApiParam("部门编号") @PathVariable Long departmentId,
                                 @RequestBody DepartmentLabelPostRequest request) {
        return departmentManager.addLabel(departmentId, request);
    }

    @ApiOperation(value = "移除部门标签", notes = "Role: organization")
    @DeleteMapping("/departments/{departmentId}/labels/{labelName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('organization')")
    @Owner(id = "#departmentId", context = DepartmentContext.class)
    public void removeLabel(@ApiParam("部门编号") @PathVariable Long departmentId,
                                    @ApiParam("标签名") @PathVariable String labelName) {
        departmentManager.removeLabel(departmentId, labelName);
    }

    @ApiOperation(value = "获取部门")
    @GetMapping("/departments/{departmentId}")
    public DepartmentVO removeLabel(@ApiParam("部门编号") @PathVariable Long departmentId) {
        return departmentManager.getDepartment(departmentId);
    }

    @ApiOperation(value = "获取组织的部门")
    @GetMapping("/organizations/{organizationId}/departments")
    public List<DepartmentVO> listOrganizationDepartments(@ApiParam("组织编号") @PathVariable Long organizationId,
                                                          BaseQueryRequest baseQueryRequest) {
        DepartmentQuery departmentQuery = new DepartmentQuery.Builder()
                .pageNum(Long.valueOf(baseQueryRequest.getPageNum()))
                .pageSize(Long.valueOf(baseQueryRequest.getPageSize()))
                .organizationId(organizationId)
                .build();
        return departmentManager.listDepartments(departmentQuery);
    }

    @ApiOperation(value = "更新部门", notes = "Role: organization")
    @PatchMapping("/departments/{departmentId}")
    @PreAuthorize("hasRole('organization')")
    @Owner(id = "#departmentId", context = DepartmentContext.class)
    public DepartmentVO updateDepartment(@ApiParam("部门编号") @PathVariable Long departmentId,
                            @Validated @RequestBody DepartmentPatchRequest request) {
        return departmentManager.updateDepartment(departmentId, request);
    }

}
