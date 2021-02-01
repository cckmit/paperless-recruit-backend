package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：组织标签控制器
 *
 * @author xhsf
 * @create 2021/2/1 00:32
 */
@ApiSupport(author = "XHSF")
@Api(tags = "组织")
@RestController
@Validated
public class OrganizationLabelController {

    private final OrganizationLabelManager organizationLabelManager;

    public OrganizationLabelController(OrganizationLabelManager organizationLabelManager) {
        this.organizationLabelManager = organizationLabelManager;
    }

    @ApiOperation(value = "创建组织标签", notes = "Role: admin")
    @PostMapping("/organizations/labels")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('admin')")
    public OrganizationLabelVO createOrganizationLabel(@RequestBody CreateOrganizationLabelRequest request) {
        return organizationLabelManager.createOrganizationLabel(request);
    }

    @ApiOperation(value = "列出组织标签")
    @GetMapping("/organizations/labels")
    public QueryResult<OrganizationLabelVO> listOrganizationLabels(QueryOrganizationLabelRequest request) {
        return organizationLabelManager.listOrganizationLabels(request);
    }

    @ApiOperation(value = "更新组织标签", notes = "Role: admin")
    @PatchMapping("/organizations/labels/{labelId}")
    @PreAuthorize("hasRole('admin')")
    public OrganizationLabelVO updateOrganizationLabel(@ApiParam("组织标签编号") @PathVariable Long labelId,
                                         @RequestBody @NotAllFieldsNull UpdateOrganizationLabelRequest request) {
        return organizationLabelManager.updateOrganizationLabel(labelId, request);
    }

}
