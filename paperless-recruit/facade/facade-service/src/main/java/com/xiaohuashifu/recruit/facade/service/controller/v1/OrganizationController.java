package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class OrganizationController {

    private final OrganizationManager organizationManager;

    private final OrganizationContext organizationContext;

    private final UserContext userContext;

    public OrganizationController(OrganizationManager organizationManager, OrganizationContext organizationContext,
                                  UserContext userContext) {
        this.organizationManager = organizationManager;
        this.organizationContext = organizationContext;
        this.userContext = userContext;
    }

    /**
     * 获取组织
     *
     * @param organizationId 组织编号
     * @return 组织
     */
    @ApiOperation(value = "获取组织")
    @GetMapping("organizations/{organizationId}")
    public OrganizationVO get(@ApiParam("组织编号") @PathVariable Long organizationId) {
        return organizationManager.getOrganization(organizationId);
    }

    /**
     * 获取用户的组织
     *
     * @param userId 用户编号
     * @return 组织
     */
    @ApiOperation(value = "获取用户的组织", notes = "ROLE: organization. Required: userId = principal.id")
    @GetMapping("users/{userId}/organizations")
    @PreAuthorize("hasRole('organization')")
    public OrganizationVO getOrganizationsByUserId(@ApiParam("用户编号") @PathVariable Long userId) {
        userContext.isOwner(userId);
        return organizationManager.getOrganizationsByUserId(userId);
    }

    /**
     * 列出组织
     *
     * @param query query
     * @return 组织列表
     */
    @ApiOperation(value = "列出组织")
    @GetMapping("organizations")
    public List<OrganizationVO> get(OrganizationQuery query) {
        return organizationManager.listOrganizations(query);
    }

    @PutMapping
    public Object updateOrganization() {
        organizationContext.isOwner(3L);
        return null;
    }

}
