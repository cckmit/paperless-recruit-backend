package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：Organization 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper
public interface OrganizationAssembler {

    OrganizationAssembler INSTANCE = Mappers.getMapper(OrganizationAssembler.class);

    OrganizationVO organizationDTO2OrganizationVO(OrganizationDTO organizationDTO);

}
