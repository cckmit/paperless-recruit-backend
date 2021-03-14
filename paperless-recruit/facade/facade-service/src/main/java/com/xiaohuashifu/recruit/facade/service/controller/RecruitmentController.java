package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.manager.RecruitmentManager;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：招新控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "招新")
@RestController
@Validated
public class RecruitmentController {

    private final RecruitmentManager recruitmentManager;

    private final OrganizationContext organizationContext;

    public RecruitmentController(RecruitmentManager recruitmentManager, OrganizationContext organizationContext) {
        this.recruitmentManager = recruitmentManager;
        this.organizationContext = organizationContext;
    }

//    @ApiOperation(value = "创建部门", notes = "Role: organization")
//    @PostMapping("/departments")
//    @PreAuthorize("hasRole('organization')")
//    public DepartmentVO createDepartment(@RequestBody CreateDepartmentRequest request) {
//        return departmentManager.createDepartment(organizationContext.getOrganizationId(), request);
//    }
//
//    @ApiOperation(value = "删除部门", notes = "Role: organization")
//    @DeleteMapping("/departments/{departmentId}")
//    @Owner(id = "#departmentId", context = DepartmentContext.class)
//    public void removeDepartment(@ApiParam("部门编号") @PathVariable Long departmentId) {
//        departmentManager.removeDepartment(departmentId);
//    }
//
//    @ApiOperation(value = "获取部门")
//    @GetMapping("/departments/{departmentId}")
//    public DepartmentVO getDepartment(@ApiParam("部门编号") @PathVariable Long departmentId) {
//        return departmentManager.getDepartment(departmentId);
//    }

    @ApiOperation(value = "列出招新")
    @GetMapping("/recruitments")
    public List<DepartmentVO> listRecruitments(RecruitmentQuery query) {
        return recruitmentManager.listRecruitments(query);
    }

//    @ApiOperation(value = "更新部门", notes = "Role: organization")
//    @PutMapping("/departments/{departmentId}")
//    @PreAuthorize("hasRole('organization')")
//    @Owner(id = "#departmentId", context = DepartmentContext.class)
//    public DepartmentVO updateDepartment(@ApiParam("部门编号") @PathVariable Long departmentId,
//                            @RequestBody @NotAllFieldsNull UpdateDepartmentRequest request) {
//        return departmentManager.updateDepartment(departmentId, request);
//    }

}
