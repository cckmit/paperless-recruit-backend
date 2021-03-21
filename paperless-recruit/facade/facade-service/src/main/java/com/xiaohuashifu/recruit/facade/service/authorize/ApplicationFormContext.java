package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.ApplicationFormManager;
import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：报名表上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class ApplicationFormContext implements Context {

    private final ApplicationFormManager applicationFormManager;

    private final UserContext userContext;

    public ApplicationFormContext(ApplicationFormManager applicationFormManager, UserContext userContext) {
        this.applicationFormManager = applicationFormManager;
        this.userContext = userContext;
    }

    /**
     * 检验是否是该报名表的拥有者
     *
     * @param applicationFormId 报名表编号
     */
    @Override
    public void isOwner(Long applicationFormId) {
        ApplicationFormVO applicationFormVO = applicationFormManager.getApplicationForm(applicationFormId);
        Long userId = applicationFormVO.getUserId();
        if (!Objects.equals(userId, userContext.getUserId())) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
