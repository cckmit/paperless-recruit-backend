package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.NotAllFieldsNull;
import com.xiaohuashifu.recruit.facade.service.authorize.ApplicationFormContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.ApplicationFormManager;
import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：报名表控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "招新")
@RestController
@Validated
public class ApplicationFormController {

    private final ApplicationFormManager applicationFormManager;

    private final UserContext userContext;

    public ApplicationFormController(ApplicationFormManager applicationFormManager, UserContext userContext) {
        this.applicationFormManager = applicationFormManager;
        this.userContext = userContext;
    }

    @ApiOperation(value = "创建报名表")
    @PostMapping("/application-forms")
    public ApplicationFormVO createApplicationForm(@RequestBody CreateApplicationFormRequest request) {
        request.setUserId(userContext.getUserId());
        return applicationFormManager.createApplicationForm(request);
    }

    @ApiOperation(value = "获取报名表")
    @GetMapping("/application-forms/{applicationFormId}")
    // TODO: 2021/3/21 这里应该判断这个报名表是不是这个用户的或者这个报名表是不是这个招新的
    public ApplicationFormVO getApplicationForm(@ApiParam("报名表编号") @PathVariable Long applicationFormId) {
        return applicationFormManager.getApplicationForm(applicationFormId);
    }

    @ApiOperation(value = "获取认证用户的报名表，通过招新编号")
    @GetMapping("/authentication/recruitments/{recruitmentId}/application-form")
    public ApplicationFormVO getAuthenticationRecruitmentApplicationForm(
            @ApiParam("招新编号") @PathVariable Long recruitmentId) {
        return applicationFormManager.getApplicationFormByUserIdAndRecruitmentId(userContext.getUserId(), recruitmentId);
    }

    @ApiOperation(value = "列出报名表")
    @GetMapping("/application-forms")
    // TODO: 2021/3/21 这里应该判断这个报名表是不是这个用户的或者这个报名表是不是这个招新的
    public QueryResult<ApplicationFormVO> listApplicationForms(ApplicationFormQuery query) {
        return applicationFormManager.listApplicationForms(query);
    }

    @ApiOperation(value = "更新报名表")
    @PutMapping("/application-forms/{applicationFormId}")
    @Owner(id = "#applicationFormId", context = ApplicationFormContext.class)
    public ApplicationFormVO updateApplicationForm(@ApiParam("报名表编号") @PathVariable Long applicationFormId,
                                          @RequestBody @NotAllFieldsNull UpdateApplicationFormRequest request) {
        request.setId(applicationFormId);
        return applicationFormManager.updateApplicationForm(request);
    }

}
