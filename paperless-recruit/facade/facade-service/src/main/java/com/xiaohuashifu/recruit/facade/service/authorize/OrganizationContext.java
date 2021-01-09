package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import org.springframework.stereotype.Component;

/**
 * 描述：组织上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class OrganizationContext {

    private final OrganizationManager organizationManager;

    private final UserContext userContext;

    public OrganizationContext(OrganizationManager organizationManager, UserContext userContext) {
        this.organizationManager = organizationManager;
        this.userContext = userContext;
    }

    /**
     * 检验是否是该组织的主体用户
     *
     * @param id 组织编号
     */
    public void authenticatePrincipal(Long id) {
        Long userId = userContext.getId();
        if (Boolean.FALSE.equals(organizationManager.authenticatePrincipal(id, userId))) {
            throw new ForbiddenException("Forbidden");
        }
    }

}