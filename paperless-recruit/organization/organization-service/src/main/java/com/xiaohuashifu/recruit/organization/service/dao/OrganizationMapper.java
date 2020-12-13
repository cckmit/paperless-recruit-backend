package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationOrganizationLabelDO;
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

    int insertLabel(OrganizationOrganizationLabelDO organizationLabelDO);

    int deleteLabelByOrganizationIdAndLabelName(@Param("organizationId") Long organizationId,
                                                @Param("labelName") String labelName);

    OrganizationDO getOrganization(Long id);

    String getOrganizationLogoUrlByOrganizationId(Long id);

    List<OrganizationDO> listOrganizations(OrganizationQuery query);

    List<String> listOrganizationLabelNamesByOrganizationId(Long organizationId);

    int count(Long id);

    int countByOrganizationName(String organizationName);

    int countLabelByOrganizationId(Long organizationId);

    int countLabelByOrganizationIdAndLabelName(@Param("organizationId") Long organizationId,
                                               @Param("labelName") String labelName);

    int updateOrganizationName(@Param("id") Long id, @Param("organizationName") String organizationName);

    int updateAbbreviationOrganizationName(@Param("id") Long id,
                                           @Param("abbreviationOrganizationName") String abbreviationOrganizationName);

    int updateIntroduction(@Param("id") Long id, @Param("introduction") String introduction);

    int updateLogoUrl(@Param("id") Long id, @Param("logoUrl") String logoUrl);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

    int increaseMemberNumber(Long id);

    int decreaseMemberNumber(Long id);

}
