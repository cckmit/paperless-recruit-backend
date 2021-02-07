package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationPositionRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationPositionRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：组织职位服务
 *
 * @author xhsf
 * @create 2020/12/13 19:22
 */
public interface OrganizationPositionService {

    /**
     * 保存组织职位
     *
     * @permission 必须是该组织的主体用户
     *
     * @param request CreateOrganizationPositionRequest
     * @return 组织职位对象
     */
    OrganizationPositionDTO createOrganizationPosition(@NotNull CreateOrganizationPositionRequest request);

    /**
     * 删除组织职位
     * 会把该组织职位的成员的职位都清除
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @param id 组织职位编号
     * @return 清除该组织是该职位的成员的职位的数量
     */
    Integer removeOrganizationPosition(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 获取组织职位
     *
     * @param id 组织职位编号
     * @return OrganizationPositionDTO 组织职位对象
     */
    OrganizationPositionDTO getOrganizationPosition(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询组织职位
     *
     * @param query 查询参数
     * @return QueryResult<OrganizationPositionDTO> 带分页参数的组织职位列表，可能返回空列表
     */
    QueryResult<OrganizationPositionDTO> listOrganizationPositions(@NotNull OrganizationPositionQuery query);

    /**
     * 更新组织职位
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @param request UpdateOrganizationPositionRequest
     * @return 更新后的组织职位对象
     */
    OrganizationPositionDTO updateOrganizationPosition(UpdateOrganizationPositionRequest request)
            throws ServiceException;

}
