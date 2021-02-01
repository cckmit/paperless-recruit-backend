package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentLabelVO;

/**
 * 描述：部门标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface DepartmentLabelManager {

    DepartmentLabelVO createDepartmentLabel(CreateDepartmentLabelRequest request);

    QueryResult<DepartmentLabelVO> listDepartmentLabels(QueryDepartmentLabelRequest request);

    DepartmentLabelVO updateDepartmentLabel(Long labelId, UpdateDepartmentLabelRequest request);

}
