package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织成员数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationMemberMapper {

    int insertOrganizationMember(OrganizationMemberDO organizationMemberDO);

    OrganizationMemberDO getOrganizationMember(Long id);

    List<OrganizationMemberDO> listOrganizationMembers(OrganizationMemberQuery query);

    Long getOrganizationId(Long id);

    Long getUserId(Long id);

    int countByOrganizationIdAndUserId(@Param("organizationId") Long organizationId, @Param("userId") Long userId);

    int updateDepartment(@Param("id") Long id, @Param("departmentId") Long departmentId);

    int updateOrganizationPosition(@Param("id") Long id, @Param("organizationPositionId") Long organizationPositionId);

    int updateMemberStatus(@Param("id") Long id, @Param("memberStatus") OrganizationMemberStatusEnum memberStatus);

    int updateOrganizationPositionByOrganizationPositionId(
            @Param("oldOrganizationPositionId") Long oldOrganizationPositionId,
            @Param("newOrganizationPositionId") Long newOrganizationPositionId);

}
