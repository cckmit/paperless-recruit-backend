package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.assembler.translator.PathToUrl;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.UrlTranslator;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.impl.UrlTranslatorImpl;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 描述：Organization 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(uses = UrlTranslatorImpl.class, componentModel = "spring")
public interface OrganizationAssembler {

    @Mapping(target = "logoUrl", qualifiedBy = {UrlTranslator.class, PathToUrl.class})
    OrganizationVO organizationDTOToOrganizationVO(OrganizationDTO organizationDTO);

    com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest organizationPutRequestToUpdateOrganizationRequest(
            UpdateOrganizationRequest organizationPutRequest);

}
