package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.authorize.OrganizationContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.authorize.RecruitmentContext;
import com.xiaohuashifu.recruit.facade.service.manager.RecruitmentManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.RecruitmentVO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "创建招新", notes = "Role: organization")
    @PostMapping("/recruitments")
    @PreAuthorize("hasRole('organization')")
    public RecruitmentVO createRecruitment(@RequestBody CreateRecruitmentRequest request) {
        return recruitmentManager.createRecruitment(organizationContext.getOrganizationId(), request);
    }

    @ApiOperation(value = "获取招新")
    @GetMapping("/recruitments/{recruitmentId}")
    public RecruitmentVO getRecruitment(@ApiParam("招新编号") @PathVariable Long recruitmentId) {
        return recruitmentManager.getRecruitment(recruitmentId);
    }

    @ApiOperation(value = "列出招新")
    @GetMapping("/recruitments")
    public QueryResult<RecruitmentVO> listRecruitments(RecruitmentQuery query) {
        return recruitmentManager.listRecruitments(query);
    }

    @ApiOperation(value = "更新招新", notes = "Role: organization")
    @PutMapping("/recruitments/{recruitmentId}")
    @PreAuthorize("hasRole('organization')")
    @Owner(id = "#recruitmentId", context = RecruitmentContext.class)
    public RecruitmentVO updateDepartment(@ApiParam("招新编号") @PathVariable Long recruitmentId,
                                          @RequestBody @NotAllFieldsNull UpdateRecruitmentRequest request) {
        return recruitmentManager.updateRecruitment(recruitmentId, request);
    }

}
