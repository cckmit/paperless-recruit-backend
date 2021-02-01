package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;

/**
 * 描述：组织标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface OrganizationLabelManager {

    OrganizationLabelVO createOrganizationLabel(CreateOrganizationLabelRequest request);

    QueryResult<OrganizationLabelVO> listOrganizationLabels(QueryOrganizationLabelRequest request);

    OrganizationLabelVO updateOrganizationLabel(Long labelId, UpdateOrganizationLabelRequest request);

}
