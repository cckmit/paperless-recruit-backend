package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：获取组织
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@RestController
@RequestMapping("organizations")
public class OrganizationController {

    private final OrganizationManager organizationManager;

    public OrganizationController(OrganizationManager organizationManager) {
        this.organizationManager = organizationManager;
    }

    /**
     * 查询组织
     *
     * @param query query
     * @return 组织列表
     */
    @GetMapping
    public Object listOrganizations(OrganizationQuery query) {
        return organizationManager.listOrganizations(query);
    }
}
