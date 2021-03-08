package com.xiaohuashifu.recruit.organization.service.assembler;

import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.user.api.request.CreateUserByEmailAuthCodeRequest;
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

    OrganizationDO updateOrganizationRequestToOrganizationDO(UpdateOrganizationRequest updateOrganizationRequest);

    CreateUserByEmailAuthCodeRequest createOrganizationRequestToCreateUserByEmailAuthCodeRequest(
            CreateOrganizationRequest createOrganizationRequest);

}
