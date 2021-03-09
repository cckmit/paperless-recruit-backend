package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationTypeAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationTypeVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationTypeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class OrganizationManagerImpl implements OrganizationManager {

    private final OrganizationAssembler organizationAssembler;

    private final OrganizationTypeAssembler organizationTypeAssembler;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private OrganizationTypeService organizationTypeService;

    public OrganizationManagerImpl(OrganizationAssembler organizationAssembler,
                                   OrganizationTypeAssembler organizationTypeAssembler) {
        this.organizationAssembler = organizationAssembler;
        this.organizationTypeAssembler = organizationTypeAssembler;
    }

    @Cacheable(key = "'organizations:' + #organizationId")
    @Override
    public OrganizationVO getOrganization(Long organizationId) {
        return organizationAssembler.organizationDTOToOrganizationVO(organizationService.getOrganization(organizationId));
    }

    @Cacheable(key = "'user:' +  #userId + ':organization'")
    @Override
    public OrganizationVO getOrganizationByUserId(Long userId) {
        return organizationAssembler.organizationDTOToOrganizationVO(organizationService.getOrganizationByUserId(userId));
    }

    @Cacheable(key = "'organizations:' + #query")
    @Override
    public QueryResult<OrganizationVO> listOrganizations(OrganizationQuery query) {
        QueryResult<OrganizationDTO> queryResult = organizationService.listOrganizations(query);
        List<OrganizationVO> organizationVOS = queryResult.getResult().stream()
                .map(organizationAssembler::organizationDTOToOrganizationVO)
                .collect(Collectors.toList());
        return new QueryResult<>(queryResult.getTotal(), organizationVOS);
    }

    @Cacheable(key = "'organization:types' + #query")
    @Override
    public QueryResult<OrganizationTypeVO> listOrganizationTypes(OrganizationTypeQuery query) {
        QueryResult<OrganizationTypeDTO> queryResult = organizationTypeService.listOrganizationTypes(query);
        List<OrganizationTypeVO> organizationTypeVOS = queryResult.getResult().stream()
                .map(organizationTypeAssembler::organizationTypeDTOToOrganizationTypeVO)
                .collect(Collectors.toList());
        return new QueryResult<>(queryResult.getTotal(), organizationTypeVOS);
    }

    @Cacheable(key = "'organization:sizes'")
    @Override
    public List<String> listOrganizationSizes() {
        return OrganizationConstants.ORGANIZATION_SIZE_LIST;
    }

    @Caching(evict = {
            @CacheEvict(key = "'organizations:' + #id"),
            @CacheEvict(key = "'user:' +  #result.userId + ':organization'")
    })
    @Override
    public OrganizationVO updateOrganization(Long id, UpdateOrganizationRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest updateOrganizationRequest =
                organizationAssembler.organizationPutRequestToUpdateOrganizationRequest(request);
        updateOrganizationRequest.setId(id);
        organizationService.updateOrganization(updateOrganizationRequest);

        return ((OrganizationManagerImpl) AopContext.currentProxy()).getOrganization(id);
    }

}
