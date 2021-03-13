package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateDepartmentRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：部门服务
 *
 * @author xhsf
 * @create 2020/12/13 21:57
 */
public interface DepartmentService {

    /**
     * 创建部门
     *
     * @param request CreateDepartmentRequest
     * @return DepartmentDTO 部门对象
     */
    DepartmentDTO createDepartment(CreateDepartmentRequest request) throws ServiceException;

    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void removeDepartment(@NotNull @Positive Long id);

    /**
     * 获取部门
     *
     * @param id 部门编号
     * @return DepartmentDTO
     */
    DepartmentDTO getDepartment(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询部门
     *
     * @param query 查询参数
     * @return QueryResult<DepartmentDTO> 查询结果，可能返回空列表
     */
    QueryResult<DepartmentDTO> listDepartments(@NotNull DepartmentQuery query);

    /**
     * 更新部门
     *
     * @param request UpdateDepartmentRequest
     * @return 更新后的部门
     */
    DepartmentDTO updateDepartment(@NotNull UpdateDepartmentRequest request);

}
