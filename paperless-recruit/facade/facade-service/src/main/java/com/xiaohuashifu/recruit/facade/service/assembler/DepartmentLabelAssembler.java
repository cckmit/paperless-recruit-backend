package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.request.QueryDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;
import org.mapstruct.Mapper;

/**
 * 描述：DepartmentLabel 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface DepartmentLabelAssembler {

    DepartmentLabelVO departmentLabelDTOToDepartmentLabelVO(DepartmentLabelDTO departmentLabelDTO);

    DepartmentLabelQuery queryDepartmentLabelRequestToDepartmentLabelQuery(QueryDepartmentLabelRequest request);

}
