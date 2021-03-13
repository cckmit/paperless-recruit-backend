package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationCoreMemberVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationTypeVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;

import java.util.List;

/**
 * 描述：组织管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface OrganizationManager {

    OrganizationCoreMemberVO createOrganizationCoreMember(Long organizationId,
                                                          CreateOrganizationCoreMemberRequest request);

    void removeOrganizationCoreMember(Long organizationId, Long organizationCoreMemberId);

    OrganizationVO getOrganization(Long organizationId);

    OrganizationVO getOrganizationByUserId(Long userId);

    OrganizationCoreMemberVO getOrganizationCoreMember(Long organizationCoreMemberId);

    QueryResult<OrganizationVO> listOrganizations(OrganizationQuery query);

    QueryResult<OrganizationTypeVO> listOrganizationTypes(OrganizationTypeQuery query);

    List<String> listOrganizationSizes();

    OrganizationVO updateOrganization(Long id, UpdateOrganizationRequest request);

    List<OrganizationCoreMemberVO> listOrganizationCoreMembersByOrganizationId(Long organizationId);

    OrganizationCoreMemberVO updateOrganizationCoreMember(Long organizationId, Long organizationCoreMemberId,
                                                          UpdateOrganizationCoreMemberRequest request);
}
