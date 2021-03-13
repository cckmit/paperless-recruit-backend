package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationCoreMemberContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationCoreMemberVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationTypeVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;
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

    @ApiOperation(value = "创建组织核心成员")
    @PreAuthorize("hasRole('organization')")
    @PostMapping("/organizations/core-members")
    public OrganizationCoreMemberVO createOrganizationCoreMember(
            @RequestBody CreateOrganizationCoreMemberRequest request) {
        return organizationManager.createOrganizationCoreMember(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "移除组织核心成员")
    @PreAuthorize("hasRole('organization')")
    @Owner(context = OrganizationCoreMemberContext.class, id = "#organizationCoreMemberId")
    @DeleteMapping("/organizations/core-members/{organizationCoreMemberId}")
    public void removeOrganizationCoreMember(@PathVariable Long organizationCoreMemberId) {
        organizationManager.removeOrganizationCoreMember(organizationContext.getOrganizationId(),
                organizationCoreMemberId);
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
    public QueryResult<OrganizationVO> listOrganizations(OrganizationQuery query) {
        return organizationManager.listOrganizations(query);
    }

    @ApiOperation(value = "列出组织类型")
    @GetMapping("/organizations/types")
    public QueryResult<OrganizationTypeVO> listOrganizationTypes(OrganizationTypeQuery query) {
        return organizationManager.listOrganizationTypes(query);
    }

    @ApiOperation(value = "列出组织规模")
    @GetMapping("/organizations/sizes")
    public List<String> listOrganizationSizes() {
        return organizationManager.listOrganizationSizes();
    }

    @ApiOperation(value = "列出组织核心成员列表通过组织编号")
    @GetMapping("/organizations/{organizationId}/core-members")
    public List<OrganizationCoreMemberVO> listOrganizationCoreMembersByOrganizationId(
            @PathVariable Long organizationId) {
        return organizationManager.listOrganizationCoreMembersByOrganizationId(organizationId);
    }

    @ApiOperation(value = "更新组织")
    @PreAuthorize("hasRole('organization')")
    @PutMapping("/organizations")
    public OrganizationVO updateOrganization(
            @RequestBody @NotAllFieldsNull UpdateOrganizationRequest request) {
        return organizationManager.updateOrganization(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "更新组织核心成员")
    @PreAuthorize("hasRole('organization')")
    @Owner(context = OrganizationCoreMemberContext.class, id = "#organizationCoreMemberId")
    @PutMapping("/organizations/core-members/{organizationCoreMemberId}")
    public OrganizationCoreMemberVO updateOrganizationCoreMember(
            @PathVariable Long organizationCoreMemberId,
            @RequestBody UpdateOrganizationCoreMemberRequest request) {
        return organizationManager.updateOrganizationCoreMember(organizationContext.getOrganizationId(),
                organizationCoreMemberId, request);
    }

}
