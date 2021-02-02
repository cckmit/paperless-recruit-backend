package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import org.mapstruct.Mapper;

/**
 * 描述：Department 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface DepartmentAssembler {

    DepartmentDTO departmentDOToDepartmentDTO(DepartmentDO departmentDO);

    DepartmentDO createDepartmentRequestToDepartmentDO(CreateDepartmentRequest createDepartmentRequest);
}
