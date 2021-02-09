package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.request.OrganizationPatchRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
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

    @Reference
    private OrganizationService organizationService;

    public OrganizationManagerImpl(OrganizationAssembler organizationAssembler) {
        this.organizationAssembler = organizationAssembler;
    }

    @Cacheable(key = "'organizations:' + #organizationId")
    @Override
    public OrganizationVO getOrganization(Long organizationId) {
        return organizationAssembler.organizationDTO2OrganizationVO(organizationService.getOrganization(organizationId));
    }

    @Cacheable(key = "'user:' +  #userId + ':organization'")
    @Override
    public OrganizationVO getOrganizationByUserId(Long userId) {
        return organizationAssembler.organizationDTO2OrganizationVO(organizationService.getOrganizationByUserId(userId));
    }

    @Cacheable(key = "'organizations:' + #query")
    @Override
    public List<OrganizationVO> listOrganizations(OrganizationQuery query) {
        QueryResult<OrganizationDTO> organizationDTOQueryResult = organizationService.listOrganizations(query);

        return organizationDTOQueryResult.getResult().stream()
                .map(organizationAssembler::organizationDTO2OrganizationVO)
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'organizations:' + #id"),
            @CacheEvict(key = "'user:' +  #result.userId + ':organization'")
    })
    @Override
    public OrganizationVO updateOrganization(Long id, OrganizationPatchRequest request) {
        if (request.getOrganizationName() != null) {
            organizationService.updateOrganizationName(id, request.getOrganizationName());
        }

        if (request.getAbbreviationOrganizationName() != null) {
            organizationService.updateAbbreviationOrganizationName(id, request.getAbbreviationOrganizationName());
        }

        if (request.getIntroduction() != null) {
            organizationService.updateIntroduction(id, request.getIntroduction());
        }

        if (request.getLogoUrl() != null) {
            organizationService.updateLogo(id, request.getLogoUrl());
        }

        return ((OrganizationManagerImpl) AopContext.currentProxy()).getOrganization(id);
    }

}
