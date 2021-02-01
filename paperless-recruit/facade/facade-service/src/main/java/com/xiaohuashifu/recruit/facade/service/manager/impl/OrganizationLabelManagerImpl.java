package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationLabelAssembler;
import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.BaseQueryRequest;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.DisableOrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
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
        Result<OrganizationLabelDTO> result = organizationLabelService.createOrganizationLabel(request.getLabelName());
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(result.getData());
    }

    @Cacheable(key = "'organizations:labels:' + #request")
    @Override
    public QueryResult<OrganizationLabelVO> listOrganizationLabels(BaseQueryRequest request) {
        Result<PageInfo<OrganizationLabelDTO>> result = organizationLabelService.listOrganizationLabels(
                new OrganizationLabelQuery.Builder()
                        .pageNum(Long.valueOf(request.getPageNum()))
                        .pageSize(Long.valueOf(request.getPageSize()))
                        .build());
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        PageInfo<OrganizationLabelDTO> data = result.getData();
        List<OrganizationLabelVO> organizationLabelVOS = data.getList().stream()
                .map(organizationLabelAssembler::organizationLabelDTOToOrganizationLabelVO)
                .collect(Collectors.toList());
        return new QueryResult<>(Long.valueOf(data.getTotal()).intValue(), organizationLabelVOS);
    }

    @CacheEvict(key = "'organizations:labels:' + #labelId", beforeInvocation = true)
    @Override
    public OrganizationLabelVO updateOrganizationLabel(Long labelId, UpdateOrganizationLabelRequest request) {
        OrganizationLabelDTO organizationLabelDTO = null;
        if (request.getAvailable() != null) {
            if (request.getAvailable()) {
                Result<OrganizationLabelDTO> result = organizationLabelService.enableOrganizationLabel(labelId);
                if (result.isFailure()) {
                    throw new ResponseEntityException(result);
                }
                organizationLabelDTO = result.getData();
            } else {
                Result<DisableOrganizationLabelDTO> result = organizationLabelService.disableOrganizationLabel(labelId);
                if (result.isFailure()) {
                    throw new ResponseEntityException(result);
                }
                organizationLabelDTO = result.getData().getOrganizationLabelDTO();
            }
        }

        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(organizationLabelDTO);
    }

}
