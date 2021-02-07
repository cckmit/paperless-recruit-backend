package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationPositionRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationPositionRequest;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationPositionDO;
import org.mapstruct.Mapper;

/**
 * 描述：OrganizationPosition 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface OrganizationPositionAssembler {

    OrganizationPositionDTO organizationPositionDOToOrganizationPositionDTO(
            OrganizationPositionDO organizationPositionDO);

    OrganizationPositionDO createOrganizationPositionRequestToOrganizationPositionDO(
            CreateOrganizationPositionRequest createOrganizationPositionRequest);

    OrganizationPositionDO updateOrganizationPositionRequestToOrganizationPositionDO(
            UpdateOrganizationPositionRequest updateOrganizationPositionRequest);

}
