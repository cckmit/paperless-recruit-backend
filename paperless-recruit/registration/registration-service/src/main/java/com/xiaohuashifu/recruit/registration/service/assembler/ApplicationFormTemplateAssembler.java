package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormTemplateDO;
import org.mapstruct.Mapper;

/**
 * 描述：报名表模板装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper(componentModel = "spring")
public interface ApplicationFormTemplateAssembler {

    ApplicationFormTemplateDTO applicationFormTemplateDOToApplicationFormTemplateDTO(
            ApplicationFormTemplateDO applicationFormTemplateDO);

    ApplicationFormTemplateDO updateApplicationFormTemplateRequestToApplicationFormTemplateDO(
            UpdateApplicationFormTemplateRequest updateApplicationFormTemplateRequest);

}
