package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationCoreMemberVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：组织核心成员上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class OrganizationCoreMemberContext implements Context {

    private final OrganizationManager organizationManager;

    private final OrganizationContext organizationContext;

    public OrganizationCoreMemberContext(OrganizationManager organizationManager,
                                         OrganizationContext organizationContext) {
        this.organizationManager = organizationManager;
        this.organizationContext = organizationContext;
    }

    /**
     * 检验是否是该组织核心成员的拥有者
     *
     * @param organizationCoreMemberId 组织核心成员编号
     */
    @Override
    public void isOwner(Long organizationCoreMemberId) {
        Long organizationId = organizationContext.getOrganizationId();
        OrganizationCoreMemberVO organizationCoreMember =
                organizationManager.getOrganizationCoreMember(organizationCoreMemberId);
        if (!Objects.equals(organizationCoreMember.getOrganizationId(), organizationId)) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
