package com.xiaohuashifu.recruit.facade.service.manager;

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

    List<DepartmentVO> listDepartments(DepartmentQuery query);

    List<DepartmentVO> listDepartmentsByOrganizationId(Long organizationId);

}
