package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentLabelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：部门标签控制器
 *
 * @author xhsf
 * @create 2021/2/1 00:32
 */
@ApiSupport(author = "XHSF")
@Api(tags = "部门")
@RestController
public class DepartmentLabelController {

    private final DepartmentLabelManager departmentLabelManager;

    public DepartmentLabelController(DepartmentLabelManager departmentLabelManager) {
        this.departmentLabelManager = departmentLabelManager;
    }


    @ApiOperation(value = "创建部门标签", notes = "Role: admin")
    @PostMapping("/departments/labels")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('admin')")
    public DepartmentLabelVO createDepartmentLabel(@RequestBody CreateDepartmentLabelRequest request) {
        return departmentLabelManager.createDepartmentLabel(request);
    }

    @ApiOperation(value = "列出部门标签")
    @GetMapping("/departments/labels")
    public QueryResult<DepartmentLabelVO> listDepartmentLabels(QueryDepartmentLabelRequest request) {
        return departmentLabelManager.listDepartmentLabels(request);
    }

    @ApiOperation(value = "更新部门标签", notes = "Role: admin")
    @PatchMapping("/departments/labels/{labelId}")
    @PreAuthorize("hasRole('admin')")
    public DepartmentLabelVO updateDepartmentLabel(@ApiParam("部门标签编号") @PathVariable Long labelId,
                                         @RequestBody UpdateDepartmentLabelRequest request) {
        return departmentLabelManager.updateDepartmentLabel(labelId, request);
    }

}
