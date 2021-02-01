package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.request.OrganizationLabelQueryRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationLabel 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationLabelAssembler {

    OrganizationLabelVO organizationLabelDTOToOrganizationLabelVO(OrganizationLabelDTO organizationLabelDTO);

    OrganizationLabelQuery organizationLabelQueryToOrganizationLabelQueryRequest(OrganizationLabelQueryRequest request);

}
