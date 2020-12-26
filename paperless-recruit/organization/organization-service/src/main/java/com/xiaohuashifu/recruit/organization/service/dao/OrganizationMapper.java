package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationMapper {

    int insertOrganization(OrganizationDO organizationDO);

    OrganizationDO getOrganization(Long id);

    String getOrganizationLogoUrl(Long id);

    Long getUserId(Long id);

    Boolean getAvailable(Long id);

    List<OrganizationDO> listOrganizations(OrganizationQuery query);

    int count(Long id);

    int countByOrganizationName(String organizationName);

    int countLabels(Long id);

    int updateOrganizationName(@Param("id") Long id, @Param("organizationName") String organizationName);

    int updateAbbreviationOrganizationName(@Param("id") Long id,
                                           @Param("abbreviationOrganizationName") String abbreviationOrganizationName);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

    int updateLogoUrl(@Param("id") Long id, @Param("logoUrl") String logoUrl);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int addLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabel(@Param("id") Long id, @Param("label") String label);

    int removeLabels(String label);

    int increaseMemberNumber(Long id);

    int decreaseMemberNumber(Long id);

}
