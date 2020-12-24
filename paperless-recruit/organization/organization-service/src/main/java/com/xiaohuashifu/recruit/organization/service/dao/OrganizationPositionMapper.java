package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationPositionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织职位数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationPositionMapper {

    int insertOrganizationPosition(OrganizationPositionDO organizationPositionDO);

    OrganizationPositionDO getOrganizationPosition(Long id);

    Long getOrganizationId(Long id);

    List<OrganizationPositionDO> listOrganizationPositions(OrganizationPositionQuery query);

    int count(Long id);

    int countByOrganizationIdPositionName(@Param("organizationId") Long organizationId,
                                          @Param("positionName") String positionName);

    int updatePositionName(@Param("id") Long id, @Param("positionName") String positionName);

    int updatePriority(@Param("id") Long id, @Param("priority") Integer priority);

    int deleteOrganizationPosition(Long id);

}
