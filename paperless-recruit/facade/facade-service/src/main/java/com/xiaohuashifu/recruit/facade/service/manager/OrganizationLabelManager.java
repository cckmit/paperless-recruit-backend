package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.request.BaseQueryRequest;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.OrganizationLabelQueryRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;

/**
 * 描述：组织标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface OrganizationLabelManager {

    OrganizationLabelVO createOrganizationLabel(CreateOrganizationLabelRequest request);

    QueryResult<OrganizationLabelVO> listOrganizationLabels(OrganizationLabelQueryRequest request);

    OrganizationLabelVO updateOrganizationLabel(Long labelId, UpdateOrganizationLabelRequest request);

}
