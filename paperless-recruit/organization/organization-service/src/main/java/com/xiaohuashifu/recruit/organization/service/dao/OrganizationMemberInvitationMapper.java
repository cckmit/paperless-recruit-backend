package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberInvitationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织成员邀请数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationMemberInvitationMapper {

    int insertOrganizationMemberInvitation(OrganizationMemberInvitationDO organizationMemberInvitationDO);

    OrganizationMemberInvitationDO getOrganizationMemberInvitation(Long id);

    List<OrganizationMemberInvitationDO> listOrganizationMemberInvitations(OrganizationMemberInvitationQuery query);

    int countByOrganizationIdAndUserIdAndInvitationStatus(
            @Param("organizationId") Long organizationId, @Param("userId") Long userId,
            @Param("invitationStatus") OrganizationMemberInvitationStatusEnum invitationStatus);

    int updateInvitationStatus(@Param("id") Long id,
                               @Param("invitationStatus") OrganizationMemberInvitationStatusEnum invitationStatus);

}
