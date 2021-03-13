package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationCoreMemberDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationCoreMember 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationCoreMemberAssembler {

    OrganizationCoreMemberDTO organizationCoreMemberDOToOrganizationCoreMemberDTO(
            OrganizationCoreMemberDO organizationCoreMemberDO);

    OrganizationCoreMemberDO createOrganizationCoreMemberRequestToOrganizationCoreMemberDO(
            CreateOrganizationCoreMemberRequest createOrganizationCoreMemberRequest);

    OrganizationCoreMemberDO updateOrganizationCoreMemberRequestToOrganizationCoreMemberDO(
            UpdateOrganizationCoreMemberRequest updateOrganizationCoreMemberRequest);
}
