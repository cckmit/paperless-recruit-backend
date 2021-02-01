package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationLabel 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationLabelAssembler {

    OrganizationLabelDTO organizationLabelDOToOrganizationLabelDTO(OrganizationLabelDO organizationlabelDO);

}
