package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.service.do0.OrganizationCoreMemberDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织核心成员数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationCoreMemberMapper {

    int insertOrganizationCoreMember(OrganizationCoreMemberDO organizationCoreMemberDO);

    int deleteOrganizationCoreMember(Long id);

    OrganizationCoreMemberDO getOrganizationCoreMember(Long id);

    Long getOrganizationId(Long id);

    List<OrganizationCoreMemberDO> listOrganizationCoreMembersByOrganizationId(Long organizationId);

    int countByOrganizationIdAndOrganizationMemberId(@Param("organizationId") Long organizationId,
                                                     @Param("organizationMemberId") Long organizationMemberId);

    int countByOrganizationId(Long organizationId);

}
