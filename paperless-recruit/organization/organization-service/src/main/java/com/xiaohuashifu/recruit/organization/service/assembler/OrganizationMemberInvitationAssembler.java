package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberInvitationDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationMemberInvitation 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationMemberInvitationAssembler {

    OrganizationMemberInvitationDTO organizationMemberInvitationDOToOrganizationMemberInvitationDTO(
            OrganizationMemberInvitationDO organizationMemberInvitationDO);

}
