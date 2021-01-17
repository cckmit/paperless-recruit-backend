package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;

import java.util.List;

/**
 * 描述：组织管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface OrganizationManager {

    OrganizationVO getOrganization(Long id);

    OrganizationVO getOrganizationsByUserId(Long userId);

    List<OrganizationVO> listOrganizations(OrganizationQuery query);

    boolean authenticatePrincipal(Long id, Long userId);


}
