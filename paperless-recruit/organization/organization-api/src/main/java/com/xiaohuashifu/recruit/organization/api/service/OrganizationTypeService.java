package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;

/**
 * 描述：组织类型服务
 *
 * @author xhsf
 * @create 2021/3/9 14:13
 */
public interface OrganizationTypeService {

    /**
     * 获取组织类型通过类型名
     *
     * @param typeName 类型名
     * @return OrganizationTypeDTO
     */
    OrganizationTypeDTO getOrganizationTypeByTypeName(String typeName) throws NotFoundServiceException;

    /**
     * 查询组织类型
     *
     * @param query OrganizationTypeQuery
     * @return QueryResult<OrganizationTypeDTO>
     */
    QueryResult<OrganizationTypeDTO> listOrganizationTypes(OrganizationTypeQuery query);

}
