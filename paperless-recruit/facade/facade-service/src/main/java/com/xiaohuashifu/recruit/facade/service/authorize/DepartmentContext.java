package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：部门上下文
 *
 * @author xhsf
 * @create 2021/1/9 20:37
 */
@Component
public class DepartmentContext implements Context {

    private final DepartmentManager departmentManager;

    private final OrganizationContext organizationContext;

    public DepartmentContext(DepartmentManager departmentManager, OrganizationContext organizationContext) {
        this.departmentManager = departmentManager;
        this.organizationContext = organizationContext;
    }

    /**
     * 检验是否是该部门的拥有者
     *
     * @param departmentId 部门编号
     */
    @Override
    public void isOwner(Long departmentId) {
        Long organizationId = organizationContext.getOrganizationId();
        DepartmentVO departmentVO = departmentManager.getDepartment(departmentId);
        if (!Objects.equals(organizationId, departmentVO.getOrganizationId())) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
