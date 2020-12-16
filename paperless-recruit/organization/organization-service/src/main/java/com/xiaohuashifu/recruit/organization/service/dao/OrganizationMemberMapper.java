package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：组织成员数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationMemberMapper {

    int insertOrganizationMember(OrganizationMemberDO organizationMemberDO);

    OrganizationMemberDO getOrganizationMember(Long id);

    int countByOrganizationIdAndUserId(@Param("organizationId") Long organizationId, @Param("userId") Long userId);

}
