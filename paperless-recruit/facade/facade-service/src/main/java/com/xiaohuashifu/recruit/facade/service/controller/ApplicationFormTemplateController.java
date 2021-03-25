package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.impl.UrlTranslatorImpl;
import com.xiaohuashifu.recruit.facade.service.authorize.ApplicationFormTemplateContext;
import com.xiaohuashifu.recruit.facade.service.authorize.Owner;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：报名表模板控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "招新")
@RestController
@Validated
public class ApplicationFormTemplateController {

    @Reference
    private ApplicationFormTemplateService applicationFormTemplateService;

    private final UrlTranslatorImpl urlTranslator;

    private final UserContext userContext;

    public ApplicationFormTemplateController(UrlTranslatorImpl urlTranslator, UserContext userContext) {
        this.urlTranslator = urlTranslator;
        this.userContext = userContext;
    }

    @ApiOperation(value = "创建报名表模板")
    @PostMapping("/application-form-templates")
    public ApplicationFormTemplateDTO createApplicationFormTemplate() {
        return assembler(applicationFormTemplateService.createApplicationFormTemplate(userContext.getUserId()));
    }

    @ApiOperation(value = "获取认证用户的报名表模板")
    @GetMapping("/authentication/application-form-templates")
    public ApplicationFormTemplateDTO getAuthenticationApplicationFormTemplate() {
        return assembler(applicationFormTemplateService.getApplicationFormTemplateByUserId(userContext.getUserId()));
    }

    @ApiOperation(value = "更新报名表模板")
    @PutMapping("/application-form-templates/{applicationFormTemplateId}")
    @Owner(id = "#applicationFormTemplateId", context = ApplicationFormTemplateContext.class)
    public ApplicationFormTemplateDTO updateApplicationFormTemplate(
            @ApiParam("报名表模板编号") @PathVariable Long applicationFormTemplateId,
            @RequestBody UpdateApplicationFormTemplateRequest request) {
        request.setId(applicationFormTemplateId);
        return assembler(applicationFormTemplateService.updateApplicationFormTemplate(request));
    }

    public ApplicationFormTemplateDTO assembler(ApplicationFormTemplateDTO applicationFormTemplateDTO) {
        applicationFormTemplateDTO.setAttachmentUrl(urlTranslator.pathToUrl(applicationFormTemplateDTO.getAttachmentUrl()));
        applicationFormTemplateDTO.setAvatarUrl(urlTranslator.pathToUrl(applicationFormTemplateDTO.getAvatarUrl()));
        return applicationFormTemplateDTO;
    }

}
