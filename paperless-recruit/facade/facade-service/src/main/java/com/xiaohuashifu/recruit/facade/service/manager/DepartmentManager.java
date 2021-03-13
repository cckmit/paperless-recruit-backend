package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;

import java.util.List;

/**
 * 描述：部门管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface DepartmentManager {

    /**
     * 创建部门
     *
     * @param organizationId 部门编号
     * @param request DepartmentPostRequest
     * @return DepartmentVO
     */
    DepartmentVO createDepartment(Long organizationId, CreateDepartmentRequest request);

    void removeDepartment(Long departmentId);

    /**
     * 获取部门
     *
     * @param departmentId 部门编号
     * @return DepartmentVO
     */
    DepartmentVO getDepartment(Long departmentId);

    /**
     * 列出部门
     *
     * @param query DepartmentQuery
     * @return List<DepartmentVO>
     */
    List<DepartmentVO> listDepartments(DepartmentQuery query);

    /**
     * 更新部门
     *
     * @param departmentId 部门编号
     * @param request DepartmentPatchRequest
     * @return DepartmentVO
     */
    DepartmentVO updateDepartment(Long departmentId, UpdateDepartmentRequest request);

}
