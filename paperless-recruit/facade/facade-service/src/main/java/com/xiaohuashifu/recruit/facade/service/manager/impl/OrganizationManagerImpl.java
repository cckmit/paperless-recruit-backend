package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(key = "'organizations:' + #id")
    @Override
    public OrganizationVO getOrganization(Long id) {
        OrganizationDTO organizationDTO = organizationService.getOrganization(id).getData();
        return organizationAssembler.organizationDTO2OrganizationVO(organizationDTO);
    }

    @Cacheable(key = "'users:' + #userId + ':organizations'")
    @Override
    public OrganizationVO getOrganizationsByUserId(Long userId) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationByUserId(userId).getData();
        return organizationAssembler.organizationDTO2OrganizationVO(organizationDTO);
    }

    @Cacheable(key = "'organizations:' + #query")
    @Override
    public List<OrganizationVO> listOrganizations(OrganizationQuery query) {
        Result<PageInfo<OrganizationDTO>> pageInfoResult = organizationService.listOrganizations(query);
        PageInfo<OrganizationDTO> pageInfo = pageInfoResult.getData();

        return pageInfo.getList().stream()
                .map(organizationAssembler::organizationDTO2OrganizationVO)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "default", key = "'organization:' + #id +':authenticate-principal:' + #userId")
    @Override
    public boolean authenticatePrincipal(Long id, Long userId) {
        return organizationService.authenticatePrincipal(id, userId);
    }
}
