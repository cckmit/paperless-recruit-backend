package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentLabelDO;
import org.mapstruct.Mapper;

/**
 * 描述：DepartmentLabel 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface DepartmentLabelAssembler {

    DepartmentLabelDTO departmentLabelDOToDepartmentLabelDTO(DepartmentLabelDO departmentLabelDO);

}
