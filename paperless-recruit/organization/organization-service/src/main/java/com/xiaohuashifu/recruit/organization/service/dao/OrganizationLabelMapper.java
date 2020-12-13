package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：组织标签数据库映射
 *
 * @author xhsf
 * @create 2020/12/8 18:49
 */
public interface OrganizationLabelMapper {

    int insertOrganizationLabel(OrganizationLabelDO organizationLabelDO);

    OrganizationLabelDO getOrganizationLabel(Long id);

    OrganizationLabelDO getOrganizationLabelByLabelName(String labelName);

    Boolean getAvailableByLabelName(String labelName);

    List<OrganizationLabelDO> listOrganizationLabels(OrganizationLabelQuery query);

    int countByLabelName(String labelName);

    int increaseReferenceNumber(Long id);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

}
