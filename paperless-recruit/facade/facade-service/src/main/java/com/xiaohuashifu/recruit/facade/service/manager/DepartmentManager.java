package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.request.DepartmentLabelPostRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentPostRequest;
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

    DepartmentVO createDepartment(Long organizationId, DepartmentPostRequest request);

    DepartmentVO addLabel(Long departmentId, DepartmentLabelPostRequest request);

    DepartmentVO getDepartment(Long departmentId);

    List<DepartmentVO> listDepartments(DepartmentQuery query);

    List<DepartmentVO> listDepartmentsByOrganizationId(Long organizationId);

}
