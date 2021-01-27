package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import org.springframework.stereotype.Component;

/**
 * 描述：组织上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class OrganizationContext implements Context {

    private final OrganizationManager organizationManager;

    private final UserContext userContext;

    public OrganizationContext(OrganizationManager organizationManager, UserContext userContext) {
        this.organizationManager = organizationManager;
        this.userContext = userContext;
    }

    /**
     * 检验是否是该组织的拥有者
     *
     * @param organizationId 组织编号
     */
    @Override
    public void isOwner(Long organizationId) {
        Long userId = userContext.getUserId();
        if (!organizationManager.authenticatePrincipal(organizationId, userId)) {
            throw new ForbiddenException("Forbidden");
        }
    }

    /**
     * 获取认证用户的组织的编号
     *
     * @return 组织编号
     */
    public Long getOrganizationId() {
        OrganizationVO organizationVO = organizationManager.getOrganizationByUserId(userContext.getUserId());
        return organizationVO.getId();
    }

}
