package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：报名表模板上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class ApplicationFormTemplateContext implements Context {

    @Reference
    private ApplicationFormTemplateService applicationFormTemplateService;

    private final UserContext userContext;

    public ApplicationFormTemplateContext(UserContext userContext) {
        this.userContext = userContext;
    }

    /**
     * 检验是否是该报名表模板的拥有者
     *
     * @param applicationFormTemplateId 报名表模板编号
     */
    @Override
    public void isOwner(Long applicationFormTemplateId) {
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                applicationFormTemplateService.getApplicationFormTemplate(applicationFormTemplateId);
        Long userId = applicationFormTemplateDTO.getUserId();
        if (!Objects.equals(userId, userContext.getUserId())) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
