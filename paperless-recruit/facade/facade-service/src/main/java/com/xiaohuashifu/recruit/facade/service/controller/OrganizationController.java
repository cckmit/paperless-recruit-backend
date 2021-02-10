package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.request.OrganizationPatchRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：组织控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "组织")
@RestController
@Validated
public class OrganizationController {

    private final OrganizationManager organizationManager;

    private final UserContext userContext;

    private final OrganizationContext organizationContext;

    public OrganizationController(OrganizationManager organizationManager, UserContext userContext,
                                  OrganizationContext organizationContext) {
        this.organizationManager = organizationManager;
        this.userContext = userContext;
        this.organizationContext = organizationContext;
    }

    @ApiOperation(value = "更新组织")
    @PreAuthorize("hasRole('organization')")
    @PostMapping("/organization/update")
    public OrganizationVO updateAuthenticatedUserOrganization(
            @RequestBody @NotAllFieldsNull OrganizationPatchRequest request) {
        return organizationManager.updateOrganization(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "获取组织")
    @GetMapping("/organizations/{organizationId}")
    public OrganizationVO getOrganization(@ApiParam("组织编号") @PathVariable Long organizationId) {
        return organizationManager.getOrganization(organizationId);
    }

    @ApiOperation(value = "获取认证用户的组织")
    @GetMapping("/authentication/organization")
    @PreAuthorize("hasRole('organization')")
    public OrganizationVO getAuthenticatedUserOrganization() {
        return organizationManager.getOrganizationByUserId(userContext.getUserId());
    }

    @ApiOperation(value = "列出组织")
    @GetMapping("/organizations")
    public List<OrganizationVO> listOrganizations(OrganizationQuery query) {
        return organizationManager.listOrganizations(query);
    }

}
