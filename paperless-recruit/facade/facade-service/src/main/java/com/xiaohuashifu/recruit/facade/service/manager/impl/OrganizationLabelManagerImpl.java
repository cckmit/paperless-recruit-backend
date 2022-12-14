package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationLabelAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class OrganizationLabelManagerImpl implements OrganizationLabelManager {

    private final OrganizationLabelAssembler organizationLabelAssembler;

    @Reference
    private OrganizationLabelService organizationLabelService;

    public OrganizationLabelManagerImpl(OrganizationLabelAssembler organizationLabelAssembler) {
        this.organizationLabelAssembler = organizationLabelAssembler;
    }

    @Override
    public OrganizationLabelVO createOrganizationLabel(CreateOrganizationLabelRequest request) {
        OrganizationLabelDTO organizationLabelDTO = organizationLabelService.createOrganizationLabel(request.getLabelName());
        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(organizationLabelDTO);
    }

    @Cacheable(key = "'organizations:labels:' + #request")
    @Override
    public QueryResult<OrganizationLabelVO> listOrganizationLabels(QueryOrganizationLabelRequest request) {
        QueryResult<OrganizationLabelDTO> data = organizationLabelService.listOrganizationLabels(
                organizationLabelAssembler.organizationLabelQueryToOrganizationLabelQueryRequest(request));
        List<OrganizationLabelVO> organizationLabelVOS = data.getResult().stream()
                .map(organizationLabelAssembler::organizationLabelDTOToOrganizationLabelVO)
                .collect(Collectors.toList());
        return new QueryResult<>(data.getTotal(), organizationLabelVOS);
    }

    @CacheEvict(key = "'organizations:labels:' + #labelId", beforeInvocation = true)
    @Override
    public OrganizationLabelVO updateOrganizationLabel(Long labelId, UpdateOrganizationLabelRequest request) {
        OrganizationLabelDTO organizationLabelDTO = null;
        if (request.getAvailable() != null) {
            if (request.getAvailable()) {
                organizationLabelDTO = organizationLabelService.enableOrganizationLabel(labelId);
            } else {
                organizationLabelDTO = organizationLabelService.disableOrganizationLabel(labelId).getOrganizationLabelDTO();
            }
        }

        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(organizationLabelDTO);
    }

}
