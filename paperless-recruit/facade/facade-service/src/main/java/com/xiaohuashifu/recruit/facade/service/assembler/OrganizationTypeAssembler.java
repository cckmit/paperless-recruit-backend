package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.OrganizationTypeVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationType 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationTypeAssembler {

    OrganizationTypeVO organizationTypeDTOToOrganizationTypeVO(OrganizationTypeDTO organizationTypeDTO);

}
