package com.xiaohuashifu.recruit.organization.service.dao;

import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;

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

    List<String> listOrganizationLabelNamesByOrganizationId(Long organizationId);
}
