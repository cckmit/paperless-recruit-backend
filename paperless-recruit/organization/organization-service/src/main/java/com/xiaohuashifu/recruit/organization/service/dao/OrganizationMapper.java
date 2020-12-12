package com.xiaohuashifu.recruit.organization.service.dao;

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

    List<String> listOrganizationLabelNamesByOrganizationId(Long organizationId);

    int count(Long id);

    int countLabelByOrganizationId(Long organizationId);

    int countLabelByOrganizationIdAndLabelName(@Param("organizationId") Long organizationId,
                                               @Param("labelName") String labelName);


}
