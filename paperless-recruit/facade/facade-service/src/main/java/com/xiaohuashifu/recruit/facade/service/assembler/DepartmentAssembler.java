package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.assembler.translator.PathToUrl;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.UrlTranslator;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.impl.UrlTranslatorImpl;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 描述：Department 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(uses = UrlTranslatorImpl.class, componentModel = "spring")
public interface DepartmentAssembler {

    @Mapping(target = "logoUrl", qualifiedBy = {UrlTranslator.class, PathToUrl.class})
    DepartmentVO departmentDTOToDepartmentVO(DepartmentDTO departmentDTO);

    com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest
    createDepartmentRequestToCreateDepartmentRequest(CreateDepartmentRequest createDepartmentRequest);

    com.xiaohuashifu.recruit.organization.api.request.UpdateDepartmentRequest
    updateDepartmentRequestToUpdateDepartmentRequest(UpdateDepartmentRequest updateDepartmentRequest);

}
