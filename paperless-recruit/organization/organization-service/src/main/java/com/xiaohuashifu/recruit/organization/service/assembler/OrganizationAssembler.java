package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import org.mapstruct.Mapper;

/**
 * 描述：Organization 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationAssembler {

    OrganizationDTO organizationDOToOrganizationDTO(OrganizationDO organizationDO);

}
