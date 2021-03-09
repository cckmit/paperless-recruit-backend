package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationTypeDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationType 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationTypeAssembler {

    OrganizationTypeDTO organizationTypeDODOToOrganizationTypeDTO(OrganizationTypeDO organizationTypeDO);

}
