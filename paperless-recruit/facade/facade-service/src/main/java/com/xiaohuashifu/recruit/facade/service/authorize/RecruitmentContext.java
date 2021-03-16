package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.RecruitmentManager;
import com.xiaohuashifu.recruit.facade.service.vo.RecruitmentVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：招新上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class RecruitmentContext implements Context {

    private final RecruitmentManager recruitmentManager;

    private final OrganizationContext organizationContext;

    public RecruitmentContext(RecruitmentManager recruitmentManager, OrganizationContext organizationContext) {
        this.recruitmentManager = recruitmentManager;
        this.organizationContext = organizationContext;
    }

    /**
     * 检验是否是该招新的拥有者
     *
     * @param recruitmentId 招新编号
     */
    @Override
    public void isOwner(Long recruitmentId) {
        Long organizationId = organizationContext.getOrganizationId();
        RecruitmentVO recruitmentVO = recruitmentManager.getRecruitment(recruitmentId);
        if (!Objects.equals(organizationId, recruitmentVO.getOrganization().getId())) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
