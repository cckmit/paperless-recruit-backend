package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.request.BaseQueryRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
public class DepartmentController {

    private final DepartmentManager departmentManager;

    public DepartmentController(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    }

    /**
     * 获取组织的部门
     *
     * @param organizationId 组织编号
     * @return 组织的部门列表
     */
    @ApiOperation(value = "获取组织的部门")
    @GetMapping("organizations/{organizationId}/departments")
    public List<DepartmentVO> listOrganizationDepartments(@ApiParam("组织编号") @PathVariable Long organizationId,
                                                          BaseQueryRequest baseQueryRequest) {
        DepartmentQuery departmentQuery = new DepartmentQuery.Builder()
                .pageNum(Long.valueOf(baseQueryRequest.getPageNum()))
                .pageSize(Long.valueOf(baseQueryRequest.getPageSize()))
                .organizationId(organizationId)
                .build();
        return departmentManager.listDepartments(departmentQuery);
    }
}
