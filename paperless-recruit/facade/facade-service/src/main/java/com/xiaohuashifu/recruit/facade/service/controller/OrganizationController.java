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

    /**
     * 获取组织
     *
     * @param organizationId 组织编号
     * @return 组织
     */
    @ApiOperation(value = "获取组织")
    @GetMapping("organizations/{organizationId}")
    public OrganizationVO getOrganization(@ApiParam("组织编号") @PathVariable Long organizationId) {
        return organizationManager.getOrganization(organizationId);
    }

    /**
     * 获取认证用户的组织
     *
     * @return 组织
     */
    @ApiOperation(value = "获取认证用户的组织")
    @GetMapping("user/organization")
    @PreAuthorize("hasRole('organization')")
    public OrganizationVO getAuthenticatedUserOrganization() {
        return organizationManager.getOrganizationByUserId(userContext.getUserId());
    }

    /**
     * 列出组织
     *
     * @param query query
     * @return 组织列表
     */
    @ApiOperation(value = "列出组织")
    @GetMapping("organizations")
    public List<OrganizationVO> listOrganizations(OrganizationQuery query) {
        return organizationManager.listOrganizations(query);
    }

    /**
     * 更新认证用户的组织
     *
     * @return 更新后的组织
     */
    @ApiOperation(value = "更新认证用户的组织")
    @PreAuthorize("hasRole('organization')")
    @PatchMapping("user/organization")
    public OrganizationVO updateAuthenticatedUserOrganization(
            @RequestBody @NotAllFieldsNull OrganizationPatchRequest request) {
        return organizationManager.updateOrganization(organizationContext.getOrganizationId(), request);
    }

}
