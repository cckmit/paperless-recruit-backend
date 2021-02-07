package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationMemberRequest;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationMember 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationMemberAssembler {

    OrganizationMemberDTO organizationMemberDOToOrganizationMemberDTO(OrganizationMemberDO organizationMemberDO);

    OrganizationMemberDO updateOrganizationMemberRequestToOrganizationMemberDO(
            UpdateOrganizationMemberRequest updateOrganizationMemberRequest);

}
